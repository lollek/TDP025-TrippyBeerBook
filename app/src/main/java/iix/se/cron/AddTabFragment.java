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

        assert view != null;
        final Button timePicker = (Button) view.findViewById(R.id.timePicker);
        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerFragment(view);
            }
        });

        final Button datePicker = (Button) view.findViewById(R.id.datePicker);
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerFragment(view);
            }
        });

        return view;
    }

    @SuppressWarnings("UnusedParameters")
    private void showDatePickerFragment(View view) {
        new DatePickerFragment().show(getFragmentManager(), "datePicker");
    }

    @SuppressWarnings("UnusedParameters")
    public void showTimePickerFragment(View view) {
        new TimePickerFragment().show(getFragmentManager(), "timePicker");
    }
}
