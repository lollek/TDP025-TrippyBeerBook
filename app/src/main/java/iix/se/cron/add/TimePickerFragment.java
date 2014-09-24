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

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {
    private final static String BUTTON_ID = "BUTTON_ID";
    private final static String TITLE_STRING_ID = "TITLE_STRING_ID";
    private Calendar mCal;

    public static TimePickerFragment newInstance(Calendar cal, int buttonID, int titleID) {
        final TimePickerFragment fragment = new TimePickerFragment();
        final Bundle args = new Bundle();
        args.putInt(BUTTON_ID, buttonID);
        args.putInt(TITLE_STRING_ID, titleID);
        fragment.setArguments(args);

        /* Link calendar to the main one */
        fragment.mCal = cal;

        return fragment;
    }

    public void updateButtonTime(Button button, String title) {
        button.setText(Html.fromHtml(String.format(
                "%s<br/><small>%02d:%02d</small>",
                title,
                mCal.get(Calendar.HOUR_OF_DAY),
                mCal.get(Calendar.MINUTE))));
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new TimePickerDialog(
                getActivity(),
                this,
                mCal.get(Calendar.HOUR_OF_DAY),
                mCal.get(Calendar.MINUTE),
                DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hour, int minute) {
        mCal.set(Calendar.HOUR_OF_DAY, hour);
        mCal.set(Calendar.MINUTE, minute);

        final Bundle args = getArguments();
        final Button button = (Button) getActivity().findViewById(args.getInt(BUTTON_ID));
        final String title = getString(args.getInt(TITLE_STRING_ID));
        updateButtonTime(button, title);
    }
}
