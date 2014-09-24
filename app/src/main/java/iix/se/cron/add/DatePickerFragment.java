package iix.se.cron.add;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    final static String BUTTON_ID = "BUTTON_ID";
    final static String TITLE_STRING_ID = "TITLE_STRING_ID";
    Calendar mCal;

    public static DatePickerFragment newInstance(int buttonID, int stringID) {
        /* Add button and button title IDs */
        final DatePickerFragment fragment = new DatePickerFragment();
        final Bundle args = new Bundle();
        args.putInt(BUTTON_ID, buttonID);
        args.putInt(TITLE_STRING_ID, stringID);
        fragment.setArguments(args);

        return fragment;
    }

    public void resetButtonDate(Button button, String title) {
        mCal = Calendar.getInstance();
        setButtonDate(button, title,
                mCal.get(Calendar.YEAR),
                mCal.get(Calendar.MONTH),
                mCal.get(Calendar.DAY_OF_MONTH));
    }

    public void setButtonDate(Button button, String title, int year, int month, int day) {
        /* Save state */
        if (mCal != null) {
            mCal.set(Calendar.YEAR, year);
            mCal.set(Calendar.MONTH, month);
            mCal.set(Calendar.DAY_OF_MONTH, day);
        }

        /* NOTE: month is 0-indexed for some reason */
        final Spanned buttonText = Html.fromHtml(
                String.format("%s<br/><small>%d-%02d-%02d</small>",
                        title, year, month +1, day));
        button.setText(buttonText);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (mCal == null) {
            mCal = Calendar.getInstance();
        }
        final int year = mCal.get(Calendar.YEAR);
        final int month = mCal.get(Calendar.MONTH);
        final int day = mCal.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        final Bundle args = getArguments();
        final String title = getString(args.getInt(TITLE_STRING_ID));
        final Button datePicker = (Button) getActivity().findViewById(args.getInt(BUTTON_ID));
        setButtonDate(datePicker, title, year, month, day);
    }
}
