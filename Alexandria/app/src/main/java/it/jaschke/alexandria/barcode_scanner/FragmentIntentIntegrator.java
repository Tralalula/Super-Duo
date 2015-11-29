// Code is taken from Sergii Pechenizky's answer on Stackoverflow
// http://stackoverflow.com/questions/20013213/zxing-onactivityresult-not-called-in-fragment-only-in-activity

package it.jaschke.alexandria.barcode_scanner;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.google.zxing.integration.android.IntentIntegrator;

public class FragmentIntentIntegrator extends IntentIntegrator {
    private final Fragment mFragment;

    public FragmentIntentIntegrator(Fragment fragment) {
        super(fragment.getActivity());
        mFragment = fragment;
    }

    @Override
    protected void startActivityForResult(Intent intent, int code) {
        mFragment.startActivityForResult(intent, code);
    }
}
