package iix.se.trippybeerbook;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import iix.se.trippybeerbook.database.Beer;
import iix.se.trippybeerbook.database.DatabaseContract;

/**
 * An activity representing a list of Beers. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link BeerDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 *
 * The activity makes heavy use of fragments. The list of items is a
 * {@link BeerListFragment} and the item details
 * (if present) is a {@link BeerDetailFragment}.
 */
public class BeerListActivity extends Activity {

    boolean mTwoPane; // Are we running in 2-pane mode (tablet) ?
    ABTest mABTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mABTest == null)
            mABTest = ABTest.getInstance(this);

        setContentView(mABTest.buttonsOnViewScreen()
                ? R.layout.activity_beer_list_withadd
                : R.layout.activity_beer_list);

        if (findViewById(R.id.beer_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((BeerListFragment) getFragmentManager()
                    .findFragmentById(R.id.beer_list))
                    .setActivateOnItemClick(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(mABTest.buttonsOnViewScreen()
                ? R.menu.actionbar_noadd
                : R.menu.actionbar,
                menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_bar_add: addItem(null); return true;
            case R.id.action_bar_item_new: return setSorting(DatabaseContract.BeerColumns._ID);
            case R.id.action_bar_item_score: return setSorting(DatabaseContract.BeerColumns.STARS);
            case R.id.action_bar_item_name: return setSorting(DatabaseContract.BeerColumns.BEER_NAME);
            case R.id.action_bar_item_brewery: return setSorting(DatabaseContract.BeerColumns.BREWERY);
            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mABTest.submitEvents();
    }

    /**
     * Handle onClick events for list items
     * @param id ID of the clicked item
     */
    public void onItemSelected(Beer id) {
        if (mTwoPane) {
            changeFragmentForTwoPane(id.mID);
        } else {
            changeFragmentForSinglePane(id.mID);
        }
    }

    /**
     * Handle onClick for the add-new-beer-button
     * @param _unused Unused
     */
    public void addItem(View _unused) {
        mABTest.recordEvent("AddButtonClick");
        if (mTwoPane) {
            changeFragmentForTwoPane(-1);
        } else {
            changeFragmentForSinglePane(-1);
        }
    }

    /**
     * Display a beer in a separate fragment.
     * In two-pane mode, show the detail view in this activity by
     * adding or replacing the detail fragment using a
     * fragment transaction.
     * @param id ID of item to display
     */
    void changeFragmentForTwoPane(long id) {
        Bundle arguments = new Bundle();
        arguments.putLong(BeerDetailFragment.ARG_ITEM_ID, id);
        BeerDetailFragment fragment = new BeerDetailFragment();
        fragment.setArguments(arguments);
        getFragmentManager().beginTransaction()
                .replace(R.id.beer_detail_container, fragment, "DetailFragment")
                .commit();
    }

    /** Display a beer in a separate fragment.
     * In single-pane mode, simply start the detail activity
     * for the selected item ID.
     * @param id ID of the item to display
     */
    void changeFragmentForSinglePane(long id) {
        Intent detailIntent = new Intent(this, BeerDetailActivity.class);
        detailIntent.putExtra(BeerDetailFragment.ARG_ITEM_ID, id);
        startActivity(detailIntent);
    }

    /**
     * Handle onClick for save-button.
     * This is only used to make Android Studio stop complaining
     */
    public void saveChanges(View view) {
        Fragment detailFragment = getFragmentManager().findFragmentByTag("DetailFragment");
        ((BeerDetailFragment)detailFragment).saveChanges();
    }

    /**
     * Handle onClick for cancel-button.
     * This is only used to make Android Studio stop complaining
     */
    public void cancelChanges(View view) {
        Fragment fragment = getFragmentManager().findFragmentByTag("DetailFragment");
        ((BeerDetailFragment)fragment).cancelChanges();
    }

    /**
     * Handle onClick for edit-button.
     * This is only used to make Android Studio stop complaining
     */
    public void editMode(View view) {
        Fragment fragment = getFragmentManager().findFragmentByTag("DetailFragment");
        ((BeerDetailFragment)fragment).editMode(view.getId());
    }

    /**
     * Change the way the list is sorted.
     * @param sort The column to sort by.
     * See {@link DatabaseContract.BeerColumns} for more info
     */
    public boolean setSorting(String sort) {
        ((BeerListFragment) getFragmentManager().findFragmentById(R.id.beer_list))
                           .setSorting(sort);
        return true;
    }
}
