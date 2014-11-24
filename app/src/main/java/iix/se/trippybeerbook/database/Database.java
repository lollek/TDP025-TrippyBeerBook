package iix.se.trippybeerbook.database;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
            final List<Beer> list = getList();
            mAdapter = new ArrayAdapter<Beer>(activity, android.R.layout.simple_list_item_2,
                    android.R.id.text1, list) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    final View view = super.getView(position, convertView, parent);
                    final TextView text1 = (TextView)view.findViewById(android.R.id.text1);
                    final TextView text2 = (TextView)view.findViewById(android.R.id.text2);
                    final Beer item = list.get(position);

                    text1.setText(item.mName);
                    text2.setText(item.mBrewery);

                    switch((int)Float.parseFloat(item.mStars)) {
                        case 1: text2.setBackgroundColor(Color.rgb(255,136,0)); break;
                        case 2: text2.setBackgroundColor(Color.rgb(255,187,51)); break;
                        case 3: text2.setBackgroundColor(Color.YELLOW); break;
                        case 4: text2.setBackgroundColor(Color.rgb(153,204,0)); break;
                        case 5: text2.setBackgroundColor(Color.rgb(102,153,0)); break;
                    }
                    return view;
                }
            };
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