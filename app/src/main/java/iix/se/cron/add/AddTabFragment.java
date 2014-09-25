package iix.se.cron.add;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Calendar;

import iix.se.cron.R;
import iix.se.cron.TabFragment;

public class AddTabFragment extends TabFragment {
    Calendar mCal;
    int mAction;
    DatePickerFragment mDatePicker;
    TimePickerFragment mTimePicker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = super.onCreateView(inflater, container, savedInstanceState);
        assert view != null;

        /* Calendar tracks both datePicker and timePicker */
        mCal = Calendar.getInstance();

        /* Init ActionPicker */
        final String[] actions = getResources().getStringArray(R.array.actions);
        final String action_title = getString(R.string.actions_title);
        final Context context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(action_title);
        builder.setItems(actions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final Button button = (Button) view.findViewById(R.id.actionPicker);
                final String text = actions[i];
                button.setText(Html.fromHtml(String.format(
                        "%s<br/><small>%s</small>",
                        action_title, text)));
                mAction = i;
            }
        });
        final AlertDialog alert = builder.create();

        final Button actionPickerButton = (Button) view.findViewById(R.id.actionPicker);
        actionPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.show();
            }
        });

        /* Init mTimePicker */
        final Button timePickerButton = (Button) view.findViewById(R.id.timePicker);
        final String timePickerTitle = getString(R.string.time_picker_title);
        mTimePicker = TimePickerFragment.newInstance(mCal, R.id.timePicker, R.string.time_picker_title);
        mTimePicker.updateButtonTime(timePickerButton, timePickerTitle);
        timePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTimePicker.show(getFragmentManager(), "timePicker");
            }
        });

        /* Init mDatePicker */
        final Button datePickerButton = (Button) view.findViewById(R.id.datePicker);
        final String datePickerTitle = getString(R.string.date_picker_title);
        mDatePicker = DatePickerFragment.newInstance(mCal, R.id.datePicker, R.string.date_picker_title);
        mDatePicker.updateButtonDate(datePickerButton, datePickerTitle);
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatePicker.show(getFragmentManager(), "datePicker");
            }
        });

        return view;
    }
}
