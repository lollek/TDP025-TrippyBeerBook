package iix.se.cron.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Calendar;

/*
 * ALL OF THESE FUNCTIONS NEED TO RUN ON ASYNC TASKS LATER!
 */

public class ActivityDatabase {
    TaskReaderDbHelper mDbHelper;

    public ActivityDatabase(Context context) {
        mDbHelper = new TaskReaderDbHelper(context);
    }

    public void addTask(Calendar cal, int action) {
        ContentValues values = new ContentValues();
        values.put(TaskReaderContract.TaskReader.TASK_TIME, cal.toString());
        values.put(TaskReaderContract.TaskReader.TASK_ACTION, action);

        SQLiteDatabase sqLiteDatabase = mDbHelper.getWritableDatabase();
        sqLiteDatabase.insertOrThrow(TaskReaderContract.TaskReader.TABLE_NAME, null, values);
    }

    public Cursor getTasks() {
        String[] projection = {
                TaskReaderContract.TaskReader._ID,
                TaskReaderContract.TaskReader.TASK_TIME,
                TaskReaderContract.TaskReader.TASK_ACTION
        };

        SQLiteDatabase sqLiteDatabase = mDbHelper.getReadableDatabase();
        return sqLiteDatabase.query(TaskReaderContract.TaskReader.TABLE_NAME,
                projection,null, null, null, null, TaskReaderContract.TaskReader._ID);
    }
}
