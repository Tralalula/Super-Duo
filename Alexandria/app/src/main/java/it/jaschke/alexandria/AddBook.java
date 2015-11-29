package it.jaschke.alexandria;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import it.jaschke.alexandria.barcode_scanner.CaptureActivityAnyOrientation;
import it.jaschke.alexandria.barcode_scanner.FragmentIntentIntegrator;
import it.jaschke.alexandria.data.AlexandriaContract;
import it.jaschke.alexandria.services.BookService;
import it.jaschke.alexandria.services.DownloadImage;


public class AddBook extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private final int LOADER_ID = 1;
    private final int ISBN_10_LENGTH = Utilities.ISBN_10_LENGTH;
    private final int ISBN_13_LENGTH = Utilities.ISBN_13_LENGTH;
    private final String ISBN_13_FIRST_THREE_NUMBERS = Utilities.ISBN_13_FIRST_THREE_NUMBERS;
    private final String EAN_CONTENT = Utilities.EAN_CONTENT;

    private EditText mEan;
    private View mRootView;

    public AddBook() {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mEan != null) {
            outState.putString(EAN_CONTENT, mEan.getText().toString());
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_add_book, container, false);
        mEan = (EditText) mRootView.findViewById(R.id.ean);

        mEan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No need
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // No need
            }

            @Override
            public void afterTextChanged(Editable editableText) {
                String ean = editableText.toString();

                // Catch ISBN 10 numbers
                if (ean.length() == ISBN_10_LENGTH && !ean.startsWith(ISBN_13_FIRST_THREE_NUMBERS)) {
                    ean += ISBN_13_FIRST_THREE_NUMBERS;
                }

                if (ean.length() < ISBN_13_LENGTH) {
                    clearFields();
                    return;
                }

                // Once we have an ISBN, start a book intent
                Intent bookIntent = new Intent(getActivity(), BookService.class);
                bookIntent.putExtra(BookService.EAN, ean);
                bookIntent.setAction(BookService.FETCH_BOOK);
                getActivity().startService(bookIntent);
                AddBook.this.restartLoader();
            }
        });

        mRootView.findViewById(R.id.scan_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentIntentIntegrator integrator = new FragmentIntentIntegrator(AddBook.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
                integrator.setCaptureActivity(CaptureActivityAnyOrientation.class);
                integrator.setOrientationLocked(false);
                integrator.setPrompt(getString(R.string.add_book_scanner_message));
                integrator.initiateScan();
            }
        });

        mRootView.findViewById(R.id.save_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEan.setText("");
            }
        });

        mRootView.findViewById(R.id.delete_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bookIntent = new Intent(getActivity(), BookService.class);
                bookIntent.putExtra(BookService.EAN, mEan.getText().toString());
                bookIntent.setAction(BookService.DELETE_BOOK);
                getActivity().startService(bookIntent);
                mEan.setText("");
            }
        });

        if (savedInstanceState != null) {
            mEan.setText(savedInstanceState.getString(EAN_CONTENT));
            mEan.setHint("");
        }

        return mRootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanResult != null) {
            mEan.setText(scanResult.getContents());
        }
    }

    private void restartLoader() {
        getLoaderManager().restartLoader(LOADER_ID, null, this);
    }

    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (mEan.getText().length() == 0) {
            return null;
        }

        String eanStr = mEan.getText().toString();
        if (eanStr.length() == ISBN_10_LENGTH && !eanStr.startsWith(ISBN_13_FIRST_THREE_NUMBERS)) {
            eanStr += ISBN_13_FIRST_THREE_NUMBERS;
        }

        return new CursorLoader(
                getActivity(),
                AlexandriaContract.BookEntry.buildFullBookUri(Long.parseLong(eanStr)),
                null,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        if (!data.moveToFirst()) {
            return;
        }

        String bookTitle = data.getString(data.getColumnIndex(AlexandriaContract.BookEntry.TITLE));
        if (bookTitle != null) {
            ((TextView) mRootView.findViewById(R.id.bookTitle)).setText(bookTitle);
        } else {
            ((TextView) mRootView.findViewById(R.id.bookTitle)).setText(getString(R.string.error_book_title_unknown));
        }

        String bookSubTitle = data.getString(data.getColumnIndex(AlexandriaContract.BookEntry.SUBTITLE));
        if (bookSubTitle != null) {
            ((TextView) mRootView.findViewById(R.id.bookSubTitle)).setText(bookSubTitle);
        } else {
            ((TextView) mRootView.findViewById(R.id.bookSubTitle)).setText(getString(R.string.error_book_subtitle_unknown));
        }

        String authors = data.getString(data.getColumnIndex(AlexandriaContract.AuthorEntry.AUTHOR));
        if (authors != null) {
            String[] authorsArr = authors.split(",");
            ((TextView) mRootView.findViewById(R.id.authors)).setLines(authorsArr.length);
            ((TextView) mRootView.findViewById(R.id.authors)).setText(authors.replace(",", "\n"));
        } else {
            ((TextView) mRootView.findViewById(R.id.authors)).setText(getString(R.string.error_book_author_unknown));
        }

        String imgUrl = data.getString(data.getColumnIndex(AlexandriaContract.BookEntry.IMAGE_URL));
        if (imgUrl != null && Patterns.WEB_URL.matcher(imgUrl).matches()) {
            new DownloadImage((ImageView) mRootView.findViewById(R.id.bookCover)).execute(imgUrl);
            mRootView.findViewById(R.id.bookCover).setVisibility(View.VISIBLE);
        }

        String categories = data.getString(data.getColumnIndex(AlexandriaContract.CategoryEntry.CATEGORY));
        if (categories != null) {
            ((TextView) mRootView.findViewById(R.id.categories)).setText(categories);
        } else {
            ((TextView) mRootView.findViewById(R.id.categories)).setText(getString(R.string.error_book_category_unknown));
        }

        mRootView.findViewById(R.id.save_button).setVisibility(View.VISIBLE);
        mRootView.findViewById(R.id.delete_button).setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {}

    private void clearFields() {
        ((TextView) mRootView.findViewById(R.id.bookTitle)).setText("");
        ((TextView) mRootView.findViewById(R.id.bookSubTitle)).setText("");
        ((TextView) mRootView.findViewById(R.id.authors)).setText("");
        ((TextView) mRootView.findViewById(R.id.categories)).setText("");

        mRootView.findViewById(R.id.bookCover).setVisibility(View.INVISIBLE);
        mRootView.findViewById(R.id.save_button).setVisibility(View.INVISIBLE);
        mRootView.findViewById(R.id.delete_button).setVisibility(View.INVISIBLE);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activity.setTitle(R.string.menu_scan_add_book);
    }
}
