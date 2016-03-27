package iix.se.trippybeerbook.database.options;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Backend class which accesses the SQLite database
 */
public class Database extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "Options.db";
    private SQLiteDatabase mCurrentDB;

    private void insertOrThrowThenClose(String key, String value) {
        final ContentValues values = new ContentValues();
        values.put(DatabaseContract.OptionsColumns.KEY, key);
        values.put(DatabaseContract.OptionsColumns.VALUE, value);

        mCurrentDB = getWritableDatabase();
        mCurrentDB.insertOrThrow(DatabaseContract.OptionsColumns.TABLE_NAME, null, values);
        mCurrentDB.close();
    }

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void close(Cursor cursor) {
        cursor.close();
        mCurrentDB.close();
    }

    public void setValue(String key, String value) {
        final String where = DatabaseContract.OptionsColumns.KEY + " = '" + key + "'";
        final ContentValues values = new ContentValues();
        values.put(DatabaseContract.OptionsColumns.KEY, key);
        values.put(DatabaseContract.OptionsColumns.VALUE, value);

        mCurrentDB = getWritableDatabase();
        mCurrentDB.update(DatabaseContract.OptionsColumns.TABLE_NAME, values, where, null);
        mCurrentDB.close();
    }

    public String getValue(String key) {
        mCurrentDB = getReadableDatabase();
        final Cursor cursor =  mCurrentDB.query(
            DatabaseContract.OptionsColumns.TABLE_NAME,
            DatabaseContract.projection,
            DatabaseContract.OptionsColumns.KEY + " = '" + key + "'",
            null,
            null,
            null,
            null);

        cursor.moveToFirst();
        KeyValuePair kv = new KeyValuePair(cursor);
        mCurrentDB.close();
        return kv.mValue;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DatabaseContract.SQL_CREATE_ENTRIES);
        for (String def: DatabaseContract.SQL_INSERT_DEFAULTS) {
            sqLiteDatabase.execSQL(def);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(DatabaseContract.SQL_DROP_ENTRIES);
        onCreate(sqLiteDatabase);
    }
}