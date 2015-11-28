package barqsoft.footballscores;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import barqsoft.footballscores.fragment.PagerFragment;

public class MainActivity extends ActionBarActivity {
    private final String SAVED_CURRENT_PAGER = "PAGER_CURRENT";
    private final String SAVED_CURRENT_SELECTED_ITEM = "SELECTED_MATCH";
    private final String SAVED_CURRENT_FRAGMENT = "PAGER_FRAGMENT";


    public static int selectedMatchId;
    public static int currentFragment = 2;
    public static String LOG_TAG = MainActivity.class.getSimpleName();
    private PagerFragment mPagerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            mPagerFragment = new PagerFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, mPagerFragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            Intent start_about = new Intent(this, AboutActivity.class);
            startActivity(start_about);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SAVED_CURRENT_PAGER, mPagerFragment.mPagerHandler.getCurrentItem());
        outState.putInt(SAVED_CURRENT_SELECTED_ITEM, selectedMatchId);
        getSupportFragmentManager().putFragment(outState, SAVED_CURRENT_FRAGMENT, mPagerFragment);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        currentFragment = savedInstanceState.getInt(SAVED_CURRENT_PAGER);
        selectedMatchId = savedInstanceState.getInt(SAVED_CURRENT_SELECTED_ITEM);
        mPagerFragment = (PagerFragment) getSupportFragmentManager()
                .getFragment(savedInstanceState, SAVED_CURRENT_FRAGMENT);

        super.onRestoreInstanceState(savedInstanceState);
    }
}
