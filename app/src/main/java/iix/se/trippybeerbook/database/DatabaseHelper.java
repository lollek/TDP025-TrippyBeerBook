package iix.se.trippybeerbook.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static int DATABASE_VERSION = 1;
    public static String DATABASE_NAME = "TrippyBeerBook.db";
    private SQLiteDatabase mCurrentDB;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void close() {
        if (mCurrentDB != null) {
            mCurrentDB.close();
        }
    }

    public Cursor query(String table, String[] columns, String selection, String sort) {
        close();
        mCurrentDB = getReadableDatabase();
        return mCurrentDB.query(table, columns, selection, null, null, null, sort);
    }

    public void insertOrThrowThenClose(String table, ContentValues values) {
        close();
        mCurrentDB = getWritableDatabase();
        mCurrentDB.insertOrThrow(table, null, values);
        mCurrentDB.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DatabaseContract.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        /* Add code here later if needed */
    }
}