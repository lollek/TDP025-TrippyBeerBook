package iix.se.trippybeerbook;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Pair;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


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
 * <p>
 * This activity also implements the required
 * {@link BeerListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class BeerListActivity extends Activity
        implements BeerListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

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

        // TODO: If exposing deep links into your app, handle intents here.
    }

    /**
     * Callback method from {@link BeerListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     * @param id
     */
    @Override
    public void onItemSelected(BeerItem.BeerType id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putInt(BeerDetailFragment.ARG_ITEM_ID, BeerItem.BeerList.indexOf(id));
            BeerDetailFragment fragment = new BeerDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .replace(R.id.beer_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, BeerDetailActivity.class);
            detailIntent.putExtra(BeerDetailFragment.ARG_ITEM_ID, BeerItem.BeerList.indexOf(id));
            startActivity(detailIntent);
        }
    }

    public void addItem(View view) {
        BeerListFragment beerListFragment = (BeerListFragment) getFragmentManager().
                findFragmentById(R.id.beer_list);
        List<Pair<Integer, String>> list = new ArrayList<Pair<Integer, String>>();
        beerListFragment.addItem(new BeerItem.BeerType("Punk IPA", "BrewDog", "IPA", "Scotland", 5.6f, list));
    }
}
