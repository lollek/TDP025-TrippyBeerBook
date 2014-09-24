package iix.se.cron.add;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;

import iix.se.cron.R;
import iix.se.cron.TabFragment;

public class AddTabFragment extends TabFragment {
    Calendar mCal;
    DatePickerFragment mDatePicker;
    TimePickerFragment mTimePicker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = super.onCreateView(inflater, container, savedInstanceState);
        assert view != null;

        /* Calendar tracks both datePicker and timePicker */
        mCal = Calendar.getInstance();

        initActionPicker(view);

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

    private void initActionPicker(View view) {
        final String[] actions = getResources().getStringArray(R.array.actions);
        final Context context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(getString(R.string.actions_title));
        builder.setItems(actions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context.getApplicationContext(), actions[i], Toast.LENGTH_SHORT).show();
            }
        });
        final AlertDialog alert = builder.create();

        final Button actionPicker = (Button) view.findViewById(R.id.actionPicker);
        actionPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.show();
            }
        });
    }
}
