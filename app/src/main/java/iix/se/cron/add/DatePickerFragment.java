package iix.se.cron.add;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    private final static String BUTTON_ID = "BUTTON_ID";
    private final static String TITLE_STRING_ID = "TITLE_STRING_ID";
    private Calendar mCal;

    public static DatePickerFragment newInstance(Calendar cal, int buttonID, int titleID) {
        final DatePickerFragment fragment = new DatePickerFragment();
        final Bundle args = new Bundle();
        args.putInt(BUTTON_ID, buttonID);
        args.putInt(TITLE_STRING_ID, titleID);
        fragment.setArguments(args);

        /* Link calendar to the main one */
        fragment.mCal = cal;

        return fragment;
    }

    public void updateButtonDate(Button button, String title) {
        button.setText(Html.fromHtml(String.format(
                "%s<br/><small>%d-%02d-%02d</small>",
                title,
                mCal.get(Calendar.YEAR),
                mCal.get(Calendar.MONTH) +1, /* NOTE: month is 0-indexed for some reason */
                mCal.get(Calendar.DAY_OF_MONTH))));
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new DatePickerDialog(
                getActivity(),
                this,
                mCal.get(Calendar.YEAR),
                mCal.get(Calendar.MONTH),
                mCal.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        mCal.set(Calendar.YEAR, year);
        mCal.set(Calendar.MONTH, month);
        mCal.set(Calendar.DAY_OF_MONTH, day);

        final Bundle args = getArguments();
        final Button button = (Button) getActivity().findViewById(args.getInt(BUTTON_ID));
        final String title = getString(args.getInt(TITLE_STRING_ID));
        updateButtonDate(button, title);
    }
}
