package iix.se.cron;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.Locale;


/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to the tab
 */
public class TabFragmentPagerAdapter extends FragmentPagerAdapter {
    MainActivity mOwner;

    public TabFragmentPagerAdapter(MainActivity owner, FragmentManager fm) {
        super(fm);
        mOwner = owner;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a TabFragment (defined as a static inner class below).
        return TabFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return mOwner.getString(R.string.title_section1).toUpperCase(l);
            case 1:
                return mOwner.getString(R.string.title_section2).toUpperCase(l);
            case 2:
                return mOwner.getString(R.string.title_section3).toUpperCase(l);
        }
        return null;
    }
}