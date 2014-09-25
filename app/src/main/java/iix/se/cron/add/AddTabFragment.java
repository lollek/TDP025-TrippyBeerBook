package iix.se.cron.add;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;

import iix.se.cron.MainActivity;
import iix.se.cron.R;
import iix.se.cron.TabFragment;

public class AddTabFragment extends TabFragment {
    Calendar mCal;
    int mAction = -1;
    DatePickerFragment mDatePicker;
    TimePickerFragment mTimePicker;
    AlertDialog mActionPicker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = super.onCreateView(inflater, container, savedInstanceState);
        assert view != null;

        resetAllInstances(view);

        /* Init mActivateTask */
        final Button activateTaskButton = (Button) view.findViewById(R.id.activateTask);
        activateTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activateNewTask();
            }
        });

        return view;
    }

    public Calendar getCal() {
        return mCal;
    }

    private void resetAllInstances(View view) {
        resetCalendar();
        resetActionPicker(view);
        resetTimePicker(view);
        resetDatePicker(view);
    }

    private void resetCalendar() {
        mCal = Calendar.getInstance();
        mCal.set(Calendar.SECOND, 0);
    }

    private void resetActionPicker(final View view) {
        final Button actionPickerButton = (Button) view.findViewById(R.id.actionPicker);
        final String actionPickerTitle = getString(R.string.actions_title);

        if (mActionPicker == null) {
            /* Build AlertDialog */
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            final String[] actions = getResources().getStringArray(R.array.actions);
            builder.setTitle(actionPickerTitle);
            builder.setItems(actions, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    actionPickerButton.setText(Html.fromHtml(String.format(
                            "%s<br/><small>%s</small>",
                            actionPickerTitle, actions[i])));
                    mAction = i;
                }
            });

            /* Set onClickListener */
            mActionPicker = builder.create();
            actionPickerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mActionPicker.show();
                }
            });
        }
        actionPickerButton.setText(actionPickerTitle);
    }


    /* Reset DatePicker button + fragment, and create it if needed */
    private void resetDatePicker(View view) {
        final Button datePickerButton = (Button) view.findViewById(R.id.datePicker);
        final String datePickerTitle = getString(R.string.date_picker_title);
        if (mDatePicker == null) {
            mDatePicker = DatePickerFragment.newInstance(this, R.id.datePicker,
                    R.string.date_picker_title);
            datePickerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDatePicker.show(getFragmentManager(), "datePicker");
                }
            });
        }
        mDatePicker.updateButtonDate(datePickerButton, datePickerTitle);
    }

    /* Reset TimePicker button + fragment, and create it if needed */
    private void resetTimePicker(View view) {
        final Button timePickerButton = (Button) view.findViewById(R.id.timePicker);
        final String timePickerTitle = getString(R.string.time_picker_title);
        if (mTimePicker == null) {
            mTimePicker = TimePickerFragment.newInstance(this, R.id.timePicker,
                    R.string.time_picker_title);
            timePickerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mTimePicker.show(getFragmentManager(), "timePicker");
                }
            });
        }
        mTimePicker.updateButtonTime(timePickerButton, timePickerTitle);
    }

    private void activateNewTask() {
        if (mCal.before(Calendar.getInstance())) {
            displayError("Time and date must be in the future");
        } else if (mAction == -1) {
            displayError("No action chosen");
        } else {
            final Activity activity = getActivity();
            ((MainActivity) activity).getActivityDatabase().addTask(mCal, mAction);
            Toast.makeText(activity.getApplicationContext(), "Task added", Toast.LENGTH_SHORT).show();
            resetAllInstances(getView());
        }
    }

    private void displayError(String s) {
        new AlertDialog.Builder(getActivity())
                .setMessage(s)
                .create()
                .show();
    }

}