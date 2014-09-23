package iix.se.cron;


import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Html;
import android.text.format.DateFormat;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar cal = Calendar.getInstance();
        final int hour = cal.get(Calendar.HOUR_OF_DAY);
        final int min = cal.get(Calendar.MINUTE);
        final boolean is24h = DateFormat.is24HourFormat(getActivity());
        return new TimePickerDialog(getActivity(), this, hour, min, is24h);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        final Button timePicker = (Button) getActivity().findViewById(R.id.timePicker);
        final String text = String.format("%s<br/><small>%d:%d</small>",
                getString(R.string.time_picker_title), hourOfDay, minute);
        timePicker.setText(Html.fromHtml(text));
    }
}
