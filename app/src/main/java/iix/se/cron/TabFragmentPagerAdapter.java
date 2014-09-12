package iix.se.cron;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;


/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to the tab
 */
public class TabFragmentPagerAdapter extends FragmentPagerAdapter {
    private static final int NUM_TABS = 3;
    private final MainActivity mOwner;

    public TabFragmentPagerAdapter(MainActivity owner, FragmentManager fm) {
        super(fm);
        mOwner = owner;
    }

    @Override
    public Fragment getItem(int position) {
        return TabFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return NUM_TABS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case TabFragment.TAB_LIST:
                return mOwner.getString(R.string.tab_header_list);
            case TabFragment.TAB_ADD:
                return mOwner.getString(R.string.tab_header_add);
            case TabFragment.TAB_SETTINGS:
                return mOwner.getString(R.string.tab_header_settings);
            default: return null;
        }
    }
}