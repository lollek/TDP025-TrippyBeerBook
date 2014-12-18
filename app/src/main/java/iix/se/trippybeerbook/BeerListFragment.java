package iix.se.trippybeerbook;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import iix.se.trippybeerbook.database.Beer;
import iix.se.trippybeerbook.database.BeerArrayAdapter;
import iix.se.trippybeerbook.database.Database;


/**
 * A list fragment representing a list of Beers. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link BeerDetailFragment}.
 */
public class BeerListFragment extends ListFragment {

    private static final String STATE_ACTIVATED_POSITION = "activated_position";
    private int mActivatedPosition = ListView.INVALID_POSITION;
    private Database mDatabase;

    // Mandatory empty constructor for screen orientation changes and stuff
    public BeerListFragment() {}

    /**
     * Add item to the beer list
     * @param item Item to add
     */
    public void addItem(Beer item) {
        mDatabase.addBeer(item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mDatabase == null)
            new AsyncCreateList().execute();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setOnItemLongClickListener(new OnItemLongClickListener());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Restore the previously serialized activated item position.
        if (savedInstanceState != null &&
            savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        BeerListActivity activity = (BeerListActivity) getActivity();
        activity.onItemSelected(mDatabase.getList(activity).get(position));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    /**
     * Change the way the list is sorted
     * @param sorting See {@link DatabaseContract.BeerColumns}
     */
    public void setSorting(String sorting) {
        mDatabase.sortBy(getActivity(), sorting, true);
        setListAdapter(mDatabase.getAdapter(getActivity()));
    }

    /**
     * Turns on activate-on-click mode.
     * When this mode is on, list items will be given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    /**
     * Set activated item
     * @param position Index of the activated item
     */
    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }

    /**
     * Class for handling long clicks on items in the beer list
     */
    private class OnItemLongClickListener implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long id) {
            final int item_pos = i;
            final Activity activity = getActivity();
            final String item_name = mDatabase.getList(activity).get(item_pos).toString();
            new AlertDialog.Builder(getActivity())
                    .setMessage(getString(R.string.delete) + " " + item_name + "?")
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mDatabase.removeBeer(mDatabase.getList(activity).get(item_pos));
                        }
                    })
                    .setNegativeButton(R.string.no, null)
                    .show();
            return true;
        }
    }

    /**
     * Async Task for populating the beer list from database
     */
    private class AsyncCreateList extends AsyncTask<Void, Void, BeerArrayAdapter> {

        @Override
        protected BeerArrayAdapter doInBackground(Void... voids) {
            mDatabase = new Database(getActivity().getApplicationContext());
            return mDatabase.getAdapter(getActivity());
        }

        @Override
        protected void onPostExecute(BeerArrayAdapter adapter) {
            super.onPostExecute(adapter);
            setListAdapter(adapter);
        }
    }
}
