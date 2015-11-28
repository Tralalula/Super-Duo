package barqsoft.footballscores.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.Date;

import barqsoft.footballscores.MainActivity;
import barqsoft.footballscores.R;
import barqsoft.footballscores.Utilities;

public class PagerFragment extends Fragment {
    public static final int NUM_PAGES = 5;
    public ViewPager mPagerHandler;
    private PageAdapter mPagerAdapter;
    private MainScreenFragment[] viewFragments = new MainScreenFragment[NUM_PAGES];

    @Override
    @SuppressWarnings("NewApi")
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_pager, container, false);
        mPagerHandler = (ViewPager) rootView.findViewById(R.id.pager);
        mPagerAdapter = new PageAdapter(getChildFragmentManager());

        for (int i = 0; i < NUM_PAGES; i++) {
            int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            Date fragmentdate;
            if (currentapiVersion >= Build.VERSION_CODES.JELLY_BEAN &&
                    getResources().getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
                fragmentdate = new Date(System.currentTimeMillis() +
                        (((-2 + ((NUM_PAGES - 1) - i)) * Utilities.TWENTY_FOUR_HOURS_IN_MILLISECONDS)));
            } else {
                fragmentdate = new Date(System.currentTimeMillis() +
                        ((i - 2) * Utilities.TWENTY_FOUR_HOURS_IN_MILLISECONDS));
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            viewFragments[i] = new MainScreenFragment();
            viewFragments[i].setFragmentDate(simpleDateFormat.format(fragmentdate));
        }
        mPagerHandler.setAdapter(mPagerAdapter);
        mPagerHandler.setCurrentItem(MainActivity.currentFragment);
        return rootView;
    }

    @SuppressWarnings("NewApi")
    private class PageAdapter extends FragmentStatePagerAdapter {
        @Override
        public Fragment getItem(int i) {
            return viewFragments[i];
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        public PageAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns the page title for the top indicator
        // Code by Udactiy student josen & grayraven42
        // https://discussions.udacity.com/t/layout-mirroring-rtl-what-is-expected/30120
        @Override
        public CharSequence getPageTitle(int position) {
            int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            if (currentapiVersion >= Build.VERSION_CODES.JELLY_BEAN
                    && mPagerHandler.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
                position = Utilities.inversePositionForRtl(position, getCount());
            }

            return getDayName(getActivity(), System.currentTimeMillis() +
                            ((position - 2) * Utilities.TWENTY_FOUR_HOURS_IN_MILLISECONDS));
        }

        public String getDayName(Context context, long dateInMillis) {
            // If the date is today, return the localized version of "Today" instead of the actual
            // day name.

            Time t = new Time();
            t.setToNow();
            int julianDay = Time.getJulianDay(dateInMillis, t.gmtoff);
            int currentJulianDay = Time.getJulianDay(System.currentTimeMillis(), t.gmtoff);
            if (julianDay == currentJulianDay) {
                return context.getString(R.string.pager_today);
            } else if (julianDay == currentJulianDay + 1) {
                return context.getString(R.string.pager_tomorrow);
            } else if (julianDay == currentJulianDay - 1) {
                return context.getString(R.string.pager_yesterday);
            } else {
                Time time = new Time();
                time.setToNow();
                // Otherwise, the format is just the day of the week (e.g "Wednesday".
                SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
                return dayFormat.format(dateInMillis);
            }
        }
    }
}
