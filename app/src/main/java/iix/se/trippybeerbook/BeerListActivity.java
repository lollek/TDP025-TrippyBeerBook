package iix.se.trippybeerbook;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import com.amazon.insights.*;

import iix.se.trippybeerbook.database.Beer;
import iix.se.trippybeerbook.database.Database;

/**
 * An activity representing a list of Beers. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link BeerDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link BeerListFragment} and the item details
 * (if present) is a {@link BeerDetailFragment}.
 */
public class BeerListActivity extends Activity {

    private boolean mTwoPane; // Are we running in 2-pane mode (tablet) ?
    private AmazonInsights mAmazonInsights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_list);

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

        if (mAmazonInsights == null) {
            /* TODO: Replace with real key */
            InsightsCredentials credentials = AmazonInsights.newCredentials("PUBLIC", "PRIVATE");
            mAmazonInsights = AmazonInsights.newInstance(credentials, getApplicationContext());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_bar_item_new: setSorting(Database.SortBy.NEW); return true;
            case R.id.action_bar_item_score: setSorting(Database.SortBy.STARS); return true;
            case R.id.action_bar_item_name: setSorting(Database.SortBy.NAME); return true;
            case R.id.action_bar_item_brewery: setSorting(Database.SortBy.BREWERY); return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    public void onItemSelected(Beer id) {
        if (mTwoPane) {
            changeFragmentForTwoPane(id.mID);
        } else {
            changeFragmentForSinglePane(id.mID);
        }
    }

    public void addItem(View view) {
        if (mTwoPane) {
            changeFragmentForTwoPane(-1);
        } else {
            changeFragmentForSinglePane(-1);
        }
    }

    void changeFragmentForTwoPane(long id) {
        // In two-pane mode, show the detail view in this activity by
        // adding or replacing the detail fragment using a
        // fragment transaction.
        Bundle arguments = new Bundle();
        arguments.putLong(BeerDetailFragment.ARG_ITEM_ID, id);
        BeerDetailFragment fragment = new BeerDetailFragment();
        fragment.setArguments(arguments);
        getFragmentManager().beginTransaction()
                .replace(R.id.beer_detail_container, fragment, "DetailFragment")
                .commit();
    }

    void changeFragmentForSinglePane(long id) {
        // In single-pane mode, simply start the detail activity
        // for the selected item ID.
        Intent detailIntent = new Intent(this, BeerDetailActivity.class);
        detailIntent.putExtra(BeerDetailFragment.ARG_ITEM_ID, id);
        startActivity(detailIntent);
    }

    public void saveChanges(View view) {
        Fragment detailFragment = getFragmentManager().findFragmentByTag("DetailFragment");
        ((BeerDetailFragment)detailFragment).saveChanges();
    }

    public void cancelChanges(View view) {
        Fragment fragment = getFragmentManager().findFragmentByTag("DetailFragment");
        ((BeerDetailFragment)fragment).cancelChanges();
    }

    public void editMode(View view) {
        Fragment fragment = getFragmentManager().findFragmentByTag("DetailFragment");
        ((BeerDetailFragment)fragment).editMode(view.getId());
    }

    public void setSorting(Database.SortBy sort) {
        ((BeerListFragment) getFragmentManager().findFragmentById(R.id.beer_list))
                           .setSorting(sort);
    }
}
