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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = super.onCreateView(inflater, container, savedInstanceState);
        assert view != null;

        initActionPicker(view);
        initTimePicker(view);
        initDatePicker(view);

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

    private void initTimePicker(View view) {
        final Button timePicker = (Button) view.findViewById(R.id.timePicker);
        final String timeTitle = getString(R.string.time_picker_title);
        final Calendar cal = Calendar.getInstance();
        final int hour = cal.get(Calendar.HOUR_OF_DAY);
        final int minute = cal.get(Calendar.MINUTE);
        timePicker.setText(TimePickerFragment.getTimeString(timeTitle, hour, minute));
        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerFragment().show(getFragmentManager(), "timePicker");
            }
        });
    }

    private void initDatePicker(View view) {
        final Button datePicker = (Button) view.findViewById(R.id.datePicker);
        final String dateTitle = getString(R.string.date_picker_title);
        final Calendar cal = Calendar.getInstance();
        final int year = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        final int day = cal.get(Calendar.DAY_OF_MONTH);
        datePicker.setText(DatePickerFragment.getDateString(dateTitle, year, month, day));
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerFragment().show(getFragmentManager(), "datePicker");
            }
        });
    }
}
