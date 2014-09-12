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

    public TabFragment() {}

    // Returns a new instance of this fragment for the given section number.
    public static TabFragment newInstance(int tabID) {
        TabFragment fragment = new TabFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, tabID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle args = getArguments();
        switch (args == null ? -1 : args.getInt(ARG_SECTION_NUMBER)) {
            case TAB_LIST:
                return inflater.inflate(R.layout.fragment_list, container, false);
            case TAB_ADD:
                return inflater.inflate(R.layout.fragment_add, container, false);
            case TAB_SETTINGS:
                return inflater.inflate(R.layout.fragment_settings, container, false);
            default: return null;
        }
    }
}