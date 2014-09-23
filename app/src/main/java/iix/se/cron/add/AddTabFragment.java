package iix.se.cron.add;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Calendar;

import iix.se.cron.R;
import iix.se.cron.TabFragment;

public class AddTabFragment extends TabFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        assert view != null;

        final Calendar cal = Calendar.getInstance();

        /* Set up time picker */
        final Button timePicker = (Button) view.findViewById(R.id.timePicker);
        final String timeTitle = getString(R.string.time_picker_title);
        final int hour = cal.get(Calendar.HOUR_OF_DAY);
        final int minute = cal.get(Calendar.MINUTE);
        timePicker.setText(TimePickerFragment.getTimeString(timeTitle, hour, minute));
        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTime(view);
            }
        });

        /* Set up date picker */
        final Button datePicker = (Button) view.findViewById(R.id.datePicker);
        final String dateTitle = getString(R.string.date_picker_title);
        final int year = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        final int day = cal.get(Calendar.DAY_OF_MONTH);
        datePicker.setText(DatePickerFragment.getDateString(dateTitle, year, month, day));
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDate(view);
            }
        });

        return view;
    }

    @SuppressWarnings("UnusedParameters")
    private void selectDate(View view) {
        new DatePickerFragment().show(getFragmentManager(), "datePicker");
    }

    @SuppressWarnings("UnusedParameters")
    public void selectTime(View view) {
        new TimePickerFragment().show(getFragmentManager(), "timePicker");
    }
}
