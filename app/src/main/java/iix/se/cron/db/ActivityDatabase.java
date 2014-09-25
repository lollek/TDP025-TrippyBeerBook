package iix.se.cron.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.Calendar;

public class ActivityDatabase {
    TaskReaderDbHelper mDbHelper;

    public ActivityDatabase(Context context) {
        mDbHelper = new TaskReaderDbHelper(context);
    }

    public void addTask(Calendar cal, int action) {
        SQLiteDatabase sqLiteDatabase = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TaskReaderContract.TaskReader.TASK_TIME, cal.toString());
        values.put(TaskReaderContract.TaskReader.TASK_ACTION, action);
        sqLiteDatabase.insert(TaskReaderContract.TaskReader.TABLE_NAME, null, values);
    }
}
