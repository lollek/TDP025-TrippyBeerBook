package iix.se.trippybeerbook.database;

import android.app.Activity;
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
            final Cursor cursor = mHelper.query(null, DatabaseContract.BeerColumns._ID);
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
        mHelper.insertOrThrowThenClose(item);
        if (mList != null) {
            mList.add(item);
        }
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    public void updateBeer(Beer item) {
        mHelper.update(item);
        if (mList != null) {
            for (int i = 0; i < mList.size(); i++) {
                if (mList.get(i).mID == item.mID) {
                    mList.remove(i);
                    mList.add(i, item);
                    break;
                }
            }
        }
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    public void removeBeer(Beer item) {
        mHelper.remove(item.mID);
        if (mList != null) {
            mList.remove(item);
        }
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    public Beer getBeerById(long id) {
        final Cursor cursor = mHelper.query(DatabaseContract.BeerColumns._ID + " = " + id, null);

        cursor.moveToFirst();
        Beer return_value = new Beer(cursor);

        cursor.close();
        mHelper.close();
        return return_value;
    }
}