package iix.se.trippybeerbook.database;

import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TaskReader.TABLE_NAME + " (" +
                    TaskReader._ID + " INTEGER PRIMARY KEY," +
                    TaskReader.BEER_NAME  + " TEXT," +
                    TaskReader.BREWERY    + " TEXT," +
                    TaskReader.BEER_TYPE  + " TEXT," +
                    TaskReader.COUNTRY    + " TEXT," +
                    TaskReader.PERCENTAGE + " TEXT)";
    public static final String SQL_DROP_ENTRIES =
            "DROP TABLE IF EXISTS " + TaskReader.TABLE_NAME;

    public static abstract class TaskReader implements BaseColumns {
        public static final String TABLE_NAME = "Beers";
        public static final String BEER_NAME = "name";
        public static final String BREWERY = "brewery";
        public static final String BEER_TYPE = "type";
        public static final String COUNTRY = "country";
        public static final String PERCENTAGE = "percentage";
    }
}