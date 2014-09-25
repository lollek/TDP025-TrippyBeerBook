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
    private AddTabFragment mOwner;

    public static DatePickerFragment newInstance(AddTabFragment owner, int buttonID, int titleID) {
        final DatePickerFragment fragment = new DatePickerFragment();
        final Bundle args = new Bundle();
        args.putInt(BUTTON_ID, buttonID);
        args.putInt(TITLE_STRING_ID, titleID);
        fragment.setArguments(args);

        /* Add pointer to calendar owner */
        fragment.mOwner = owner;

        return fragment;
    }

    public void updateButtonDate(Button button, String title) {
        Calendar cal = mOwner.getCal();
        button.setText(Html.fromHtml(String.format(
                "%s<br/><small>%d-%02d-%02d</small>",
                title,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH) + 1, /* NOTE: month is 0-indexed for some reason */
                cal.get(Calendar.DAY_OF_MONTH))));
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar cal = mOwner.getCal();
        return new DatePickerDialog(
                getActivity(),
                this,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar cal = mOwner.getCal();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);

        final Bundle args = getArguments();
        final Button button = (Button) getActivity().findViewById(args.getInt(BUTTON_ID));
        final String title = getString(args.getInt(TITLE_STRING_ID));
        updateButtonDate(button, title);
    }
}
