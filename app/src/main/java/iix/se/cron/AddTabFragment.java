package iix.se.cron;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class AddTabFragment extends TabFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        final Button timePicker = (Button) view.findViewById(R.id.timePicker);
        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerFragment(view);
            }
        });

        return view;
    }

    /* Time Picker in fragment_add */
    public void showTimePickerFragment(View view) {
        new TimePickerFragment().show(getFragmentManager(), "timePicker");
    }
}
