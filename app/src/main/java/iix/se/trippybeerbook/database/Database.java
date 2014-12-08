package iix.se.trippybeerbook.database;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/*
 * ALL OF THESE FUNCTIONS NEED TO RUN ON ASYNC TASKS LATER!
 */

public class Database {
    private DatabaseHelper mHelper;
    private BeerArrayAdapter mAdapter;
    private List<Beer> mList;
    private String mCurrentSort;

    public static enum SortBy {
        NEW,
        NAME,
        BREWERY,
        STARS
    }

    public Database(Context context) {
        this(context, SortBy.NEW);
    }

    public Database(Context context, SortBy sorting) {
        mHelper = new DatabaseHelper(context);
        sortBy(sorting);
    }

    public List<Beer> getList() {
        if (mList == null) {
            mList = new ArrayList<Beer>();
            final Cursor cursor = mHelper.query(null, mCurrentSort);
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

    public BeerArrayAdapter getAdapter(Activity activity) {
        if (mAdapter == null) {
            final List<Beer> list = getList();
            mAdapter = new BeerArrayAdapter(activity, list);
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

    public void sortBy(SortBy sort) {
        switch(sort) {
            case NEW: mCurrentSort = DatabaseContract.BeerColumns._ID; break;
            case NAME: mCurrentSort = DatabaseContract.BeerColumns.BEER_NAME; break;
            case BREWERY: mCurrentSort = DatabaseContract.BeerColumns.BREWERY; break;
            case STARS: mCurrentSort = DatabaseContract.BeerColumns.STARS + " DESC"; break;
        }
    }
}