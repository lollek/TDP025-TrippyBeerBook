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
     * Constructor
     * @param context Context
     */
     public Database(Context context) {
        mHelper = new DatabaseHelper(context);
    }

    /**
     * Get all database items
     * @param activity Activity
     * @return A list of database items
     */
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
     * @param activity Activity
     * @return An adapter of the database item list
     */
     public BeerArrayAdapter getAdapter(Activity activity) {
        if (mAdapter == null)
            mAdapter = new BeerArrayAdapter(activity, getList(activity));
        return mAdapter;
    }

    /**
     * Add item to list
     * @param item Item to add
     */
    public void addBeer(Beer item) {
        mHelper.insertOrThrowThenClose(item);
        if (mList != null) {
            mList.add(item);
        }
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Modify an existing item
     * @param item Item to modify
     */
    public void updateBeer(Beer item) {
        mHelper.update(item);
        if (mList != null) {
            int pos = mList.indexOf(item);
            if (pos != -1)
                mList.set(pos, item);
        }
        if (mAdapter != null)
            mAdapter.notifyDataSetChanged();
    }

    /**
     * Remove an existing item
     * @param item Item to modify
     */
    public void removeBeer(Beer item) {
        mHelper.remove(item.mID);
        if (mList != null) {
            mList.remove(item);
            if (mAdapter != null)
                mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * NB: This may well crash the app if the id is not found
     * @param id ID to find item from
     * @return Return the item with the given id.
     */
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
     * NB: You will need to setListAdapter() after this
     * @param activity Activity
     * @param sort Columnt to sort by
     * @param refresh Should we reset the list and adapter?
     */
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

    /**
     * @param activity Activity
     * @return Return how we should sort the list
     */
    String getCurrentSorting(Activity activity) {
        String sortingString = activity
                .getPreferences(Context.MODE_PRIVATE)
                .getString("sort", DatabaseContract.BeerColumns._ID);
        if (sortingString.equals(DatabaseContract.BeerColumns.STARS))
            sortingString += " DESC";
        return sortingString;
    }
}