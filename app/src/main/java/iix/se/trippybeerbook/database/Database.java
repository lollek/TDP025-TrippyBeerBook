package iix.se.trippybeerbook.database;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/*
 * ALL OF THESE FUNCTIONS NEED TO RUN ON ASYNC TASKS LATER!
 */

public class Database {
    private DatabaseHelper mHelper;
    private ArrayAdapter<Beer> mAdapter;
    private List<Beer> mList;

    public Database(Context context) {
        mHelper = new DatabaseHelper(context);
    }

    public List<Beer> getList() {
        if (mList == null) {
            mList = new ArrayList<Beer>();
            final String[] projection = DatabaseContract.projection;
            final Cursor cursor = mHelper.query(
                    DatabaseContract.BeerColumns.TABLE_NAME,
                    projection,
                    null,
                    DatabaseContract.BeerColumns._ID);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Beer beer = new Beer(cursor);
                mList.add(beer);
                cursor.moveToNext();
            }

            cursor.close();
            mHelper.close();

            if (mAdapter != null) {
                mAdapter.notifyDataSetChanged();
            }
        }
        return mList;
    }

    public ArrayAdapter<Beer> getAdapter(Activity activity) {
        if (mAdapter == null) {
            List<Beer> list = getList();
            mAdapter = new ArrayAdapter<Beer>(
                    activity,
                    android.R.layout.simple_list_item_activated_1,
                    android.R.id.text1,
                    list);
        }
        return mAdapter;
    }

    public void addBeer(Beer item) {
        final ContentValues values = new ContentValues();
        values.put(DatabaseContract.BeerColumns.BEER_NAME, item.mName);
        values.put(DatabaseContract.BeerColumns.BREWERY, item.mBrewery);
        values.put(DatabaseContract.BeerColumns.BEER_TYPE, item.mBeerType);
        values.put(DatabaseContract.BeerColumns.COUNTRY, item.mCountry);
        values.put(DatabaseContract.BeerColumns.PERCENTAGE, item.mPercentage);

        mHelper.insertOrThrowThenClose(DatabaseContract.BeerColumns.TABLE_NAME, values);
        if (mList != null) {
            mList.add(item);
        }
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    public Beer getBeerById(long id) {
        final String[] projection = DatabaseContract.projection;
        final Cursor cursor = mHelper.query(
                DatabaseContract.BeerColumns.TABLE_NAME,
                projection,
                DatabaseContract.BeerColumns._ID + " = " + id,
                null);

        cursor.moveToFirst();
        Beer return_value = new Beer(cursor);

        cursor.close();
        mHelper.close();
        return return_value;
    }
}