package iix.se.cron.view;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Html;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import iix.se.cron.MainActivity;
import iix.se.cron.R;
import iix.se.cron.TabFragment;
import iix.se.cron.db.ActivityDatabase;
import iix.se.cron.db.TaskReaderContract;

public class ViewTabFragment extends TabFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        assert view != null;

        ActivityDatabase db = ((MainActivity) getActivity()).getActivityDatabase();
        Cursor cursor = db.getTasks();
        cursor.moveToFirst();
        for (int i = cursor.getCount(); i > 0; --i) {
            final int id_pos = cursor.getColumnIndexOrThrow(TaskReaderContract.TaskReader._ID);
            final int cal_pos = cursor.getColumnIndexOrThrow(TaskReaderContract.TaskReader.TASK_TIME);
            final int action_pos = cursor.getColumnIndexOrThrow(TaskReaderContract.TaskReader.TASK_ACTION);

            final String _ID = cursor.getString(id_pos);
            final String cal = cursor.getString(cal_pos);
            final int action = cursor.getInt(action_pos);
            addTask(view, _ID, cal ,action);
        }
        Toast.makeText(getActivity().getApplicationContext(), cursor.getCount() + " tasks shown",
                Toast.LENGTH_SHORT).show();
        cursor.close();
        return view;
    }

    private void addTask(View view, String _ID, String cal, int action) {
        RelativeLayout list = (RelativeLayout) view.findViewById(R.id.task_list);
        Button button = new Button(view.getContext());
        button.setText(Html.fromHtml(String.format("%s<br/>%s<br/>%d", new Object[]{_ID, cal, action})));
        RelativeLayout.LayoutParams lp =
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        list.addView(button, lp);
    }
}
