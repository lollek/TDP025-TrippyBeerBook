package iix.se.cron;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

// Fragments for the tabs
public class TabFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    public static final int TAB_LIST = 0;
    public static final int TAB_ADD = 1;
    public static final int TAB_SETTINGS = 2;
    public static final int NUM_TABS = 3;

    // Returns a new instance of this fragment for the given section number.
    public static TabFragment newInstance(int tabID) {
        TabFragment fragment;
        switch(tabID) {
            case TAB_ADD: fragment = new AddTabFragment(); break;
            default:      fragment = new TabFragment(); break;
        }
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, tabID);
        fragment.setArguments(args);
        return fragment;
    }

    public static int getTabNameID(int tabID) {
        switch (tabID) {
            case TAB_LIST:     return R.string.tab_header_list;
            case TAB_ADD:      return R.string.tab_header_add;
            case TAB_SETTINGS: return R.string.tab_header_settings;
            default:           return -1;
        }
    }

    public static int getTabLayoutID(int tabID) {
        switch(tabID) {
            case TAB_LIST:     return R.layout.fragment_list;
            case TAB_ADD:      return R.layout.fragment_add;
            case TAB_SETTINGS: return R.layout.fragment_settings;
            default:           return -1;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle args = getArguments();
        int fragmentID = getTabLayoutID(args == null ? -1 : args.getInt(ARG_SECTION_NUMBER));
        return inflater.inflate(fragmentID, container, false);
    }
}