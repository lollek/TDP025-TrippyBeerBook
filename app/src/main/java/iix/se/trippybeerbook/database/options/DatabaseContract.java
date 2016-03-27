package iix.se.trippybeerbook.database.options;

import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String SQL_CREATE_ENTRIES =
        "CREATE TABLE " + OptionsColumns.TABLE_NAME + " (" +
            OptionsColumns._ID      + " INTEGER PRIMARY KEY," +
            OptionsColumns.KEY   + " TEXT," +
            OptionsColumns.VALUE    + " TEXT)";

    public static final String[] SQL_INSERT_DEFAULTS = {
        "INSERT INTO " + OptionsColumns.TABLE_NAME + " (" +
        OptionsColumns.KEY + ", " + OptionsColumns.VALUE + ") " +
        "VALUES (" + "'colorful'" + ", " + "'false')"
    };

    public static final String SQL_DROP_ENTRIES =
        "DROP TABLE IF EXISTS " + OptionsColumns.TABLE_NAME;

    public static abstract class OptionsColumns implements BaseColumns {
        public static final String TABLE_NAME = "Options";
        public static final String KEY = "key";
        public static final String VALUE = "value";
    }

    public static final String[] projection = {
            OptionsColumns._ID,
            OptionsColumns.KEY,
            OptionsColumns.VALUE
    };
}