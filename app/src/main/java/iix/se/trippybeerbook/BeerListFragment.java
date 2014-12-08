package iix.se.trippybeerbook;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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

    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position.
     * Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    /**
     * The current activated item position.
     * Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;

    /**
     * Database for storing items
     */
    private Database mDatabase;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BeerListFragment() {}

    public void addItem(Beer item) {
        mDatabase.addBeer(item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mDatabase == null) {
            String tmpStr = getActivity().getPreferences(Context.MODE_PRIVATE).getString("sort", "");
            Database.SortBy sorting = tmpStr.isEmpty()
                    ? Database.SortBy.NEW
                    : Database.SortBy.valueOf(tmpStr);
            mDatabase = new Database(getActivity().getApplicationContext(), sorting);
        }
        setListAdapter(mDatabase.getAdapter(getActivity()));
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
        ((BeerListActivity)getActivity())
                .onItemSelected(mDatabase.getList().get(position));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    public void setSorting(Database.SortBy sorting) {
        final SharedPreferences.Editor editor =
                getActivity().getPreferences(Context.MODE_PRIVATE).edit();
        editor.putString("sort", sorting.toString());
        editor.apply();

        mDatabase.sortBy(sorting);
        BeerArrayAdapter adapter = mDatabase.getAdapter(getActivity());
        setListAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void resetListAdapter() {
        setListAdapter(mDatabase.getAdapter(getActivity()));
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }

    private class OnItemLongClickListener implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long id) {
            final int item_pos = i;
            final String item_name = mDatabase.getList().get(item_pos).toString();
            new AlertDialog.Builder(getActivity())
                    .setMessage(getString(R.string.delete) + " " + item_name + "?")
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mDatabase.removeBeer(mDatabase.getList().get(item_pos));
                        }
                    })
                    .setNegativeButton(R.string.no, null)
                    .show();
            return true;
        }
    }
}
