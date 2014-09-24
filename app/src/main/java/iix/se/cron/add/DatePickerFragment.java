package iix.se.cron.add;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;

import iix.se.cron.R;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {


    public static CharSequence getDateString(String title, int year, int month, int day) {
        return Html.fromHtml(String.format("%s<br/><small>%d-%02d-%02d</small>",
                title, year, month, day));
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar cal = Calendar.getInstance();
        final int year = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        final int day = cal.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
         /* NOTE: month is 0-indexed for some reason */
        final Button datePicker = (Button) getActivity().findViewById(R.id.datePicker);
        datePicker.setText(getDateString(getString(R.string.date_picker_title), year, month +1, day));
    }

}
