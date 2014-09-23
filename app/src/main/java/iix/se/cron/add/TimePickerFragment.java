package iix.se.cron.add;


import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Html;
import android.text.format.DateFormat;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;

import iix.se.cron.R;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    public static CharSequence getTimeString(String title, int hour, int minute) {
        return Html.fromHtml(String.format("%s<br/><small>%02d:%02d</small>",
                title, hour, minute));
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar cal = Calendar.getInstance();
        final int hour = cal.get(Calendar.HOUR_OF_DAY);
        final int min = cal.get(Calendar.MINUTE);
        final boolean is24h = DateFormat.is24HourFormat(getActivity());
        return new TimePickerDialog(getActivity(), this, hour, min, is24h);
    }

    @Override
    public void onTimeSet(TimePicker view, int hour, int minute) {
        final Button timePicker = (Button) getActivity().findViewById(R.id.timePicker);
        timePicker.setText(getTimeString(getString(R.string.time_picker_title), hour, minute));
    }
}
