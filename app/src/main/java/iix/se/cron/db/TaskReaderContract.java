package iix.se.cron.db;

import android.provider.BaseColumns;

public class TaskReaderContract {
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TaskReader.TABLE_NAME + " (" +
                    TaskReader._ID + " INTEGER PRIMARY KEY," +
                    TaskReader.TASK_TIME + " TEXT," +
                    TaskReader.TASK_ACTION + " INTEGER)";
    public static final String SQL_DROP_ENTRIES =
            "DROP TABLE IF EXISTS " + TaskReader.TABLE_NAME;

    public static abstract class TaskReader implements BaseColumns {
        public static final String TABLE_NAME = "Tasks";
        public static final String TASK_TIME = "time";
        public static final String TASK_ACTION = "action";
    }
}
