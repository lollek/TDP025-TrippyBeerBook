package iix.se.cron;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to the tab
 */
public class TabFragmentPagerAdapter extends FragmentPagerAdapter {
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
    public int getCount() {return TabFragment.NUM_TABS; }

    @Override
    public CharSequence getPageTitle(int position) {
        return mOwner.getString(TabFragment.getTabNameID(position));
    }
}