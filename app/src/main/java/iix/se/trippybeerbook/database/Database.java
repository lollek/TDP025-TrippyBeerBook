package iix.se.trippybeerbook.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import iix.se.trippybeerbook.BeerItem;

/*
 * ALL OF THESE FUNCTIONS NEED TO RUN ON ASYNC TASKS LATER!
 */

public class Database {
    DatabaseHelper mHelper;

    public Database(Context context) {
        mHelper = new DatabaseHelper(context);
    }

    public void addBeer(BeerItem.Beer item) {
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.BeerColumns.BEER_NAME, item.mName);
        values.put(DatabaseContract.BeerColumns.BREWERY, item.mBrewery);
        values.put(DatabaseContract.BeerColumns.BEER_TYPE, item.mBeerType);
        values.put(DatabaseContract.BeerColumns.COUNTRY, item.mCountry);
        values.put(DatabaseContract.BeerColumns.PERCENTAGE, item.mPercentage);

        SQLiteDatabase sqLiteDatabase = mHelper.getWritableDatabase();
        sqLiteDatabase.insertOrThrow(DatabaseContract.BeerColumns.TABLE_NAME, null, values);
    }

    public Cursor getBeers() {
        String[] projection = {
                DatabaseContract.BeerColumns._ID,
                DatabaseContract.BeerColumns.BEER_NAME,
                DatabaseContract.BeerColumns.BREWERY,
                DatabaseContract.BeerColumns.BEER_TYPE,
                DatabaseContract.BeerColumns.COUNTRY,
                DatabaseContract.BeerColumns.PERCENTAGE
        };

        SQLiteDatabase sqLiteDatabase = mHelper.getReadableDatabase();
        return sqLiteDatabase.query(DatabaseContract.BeerColumns.TABLE_NAME,
                projection, null, null, null, null, DatabaseContract.BeerColumns._ID);
    }
}