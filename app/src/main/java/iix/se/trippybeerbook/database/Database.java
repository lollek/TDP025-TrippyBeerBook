package iix.se.trippybeerbook.database;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Database is an abstraction for interacting with the SQLite database.
 * The real action takes place in {@link DatabaseHelper} */
public class Database {
    DatabaseHelper mHelper;             /* SQLite backend */
    BeerArrayAdapter mAdapter;          /* Adapter for mList */
    List<Beer> mList;                   /* List of items in database */

    /**
     * Standard constructor
     * NB: This should not run on the UI-thread! */
    public Database(Context context) {
        mHelper = new DatabaseHelper(context);
    }

    /**
     * Returns list of database items
     * NB: This should not run on the UI-thread! */
    public List<Beer> getList(Activity activity) {
        if (mList == null) {
            mList = new ArrayList<Beer>();
            final Cursor cursor = mHelper.query(null, getCurrentSorting(activity));

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Beer beer = new Beer(cursor);
                mList.add(beer);
                cursor.moveToNext();
            }

            cursor.close();
            mHelper.close();

            if (mAdapter != null)
                mAdapter.notifyDataSetChanged();
        }
        return mList;
    }

    /**
     * Returns an adapter for the database's list
     * NB: There might be issues if this runs on the UI-thread! */
    public BeerArrayAdapter getAdapter(Activity activity) {
        if (mAdapter == null)
            mAdapter = new BeerArrayAdapter(activity, getList(activity));
        return mAdapter;
    }

    /* Add an item, TODO: Make this run on a thread */
    public void addBeer(Beer item) {
        mHelper.insertOrThrowThenClose(item);
        if (mList != null) {
            mList.add(item);
        }
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    /* Modify an existing item, TODO: Make this run on a thread */
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

    /* Remove an existing item, TODO: Make this run on a thread */
    public void removeBeer(Beer item) {
        mHelper.remove(item.mID);
        if (mList != null) {
            mList.remove(item);
        }
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Return the item with the given id.
     * NB: This should not be run on the UI-thread
     * NB: This may well crash the app if the id is not found */
    public Beer getBeerById(long id) {
        final Cursor cursor = mHelper.query(DatabaseContract.BeerColumns._ID + " = " + id, null);

        cursor.moveToFirst();
        Beer return_value = new Beer(cursor);

        cursor.close();
        mHelper.close();
        return return_value;
    }

    /**
     * Change the way the database is sorted.
     * NB: You will need to setListAdapter() after this */
    public void sortBy(Activity activity, String sort, Boolean refresh) {
        final SharedPreferences.Editor editor =
                activity.getPreferences(Context.MODE_PRIVATE).edit();
        editor.putString("sort", sort);
        editor.apply();
        if (refresh) {
            mList = null;
            mAdapter = null;
        }
    }

    /* Returns how we should sort the list */
    String getCurrentSorting(Activity activity) {
        String sortingString = activity
                .getPreferences(Context.MODE_PRIVATE)
                .getString("sort", DatabaseContract.BeerColumns._ID);
        if (sortingString.equals(DatabaseContract.BeerColumns.STARS))
            sortingString += " DESC";
        return sortingString;
    }
}