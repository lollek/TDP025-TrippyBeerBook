package iix.se.trippybeerbook;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;

/**
 * An activity representing a single Beer detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link BeerListActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link BeerDetailFragment}.
 */
public class BeerDetailActivity extends Activity {
    boolean newItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_detail);

        // Show the Up button in the action bar.
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Add fragment if we don't already have one
        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            long mItemID = getIntent().getLongExtra(BeerDetailFragment.ARG_ITEM_ID, 0);
            newItem = mItemID == -1;
            arguments.putLong(BeerDetailFragment.ARG_ITEM_ID, mItemID);
            BeerDetailFragment fragment = new BeerDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .add(R.id.beer_detail_container, fragment, "DetailFragment")
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                NavUtils.navigateUpTo(this,
                        new Intent(this, BeerListActivity.class));
                return true;

            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, BeerListActivity.class));
    }

    public void saveChanges(View view) {
        Fragment detailFragment = getFragmentManager().findFragmentByTag("DetailFragment");
        ((BeerDetailFragment)detailFragment).saveChanges();

        if (newItem) {
            NavUtils.navigateUpTo(this, new Intent(this, BeerListActivity.class));
        }
    }

    public void cancelChanges(View view) {
        Fragment fragment = getFragmentManager().findFragmentByTag("DetailFragment");
        ((BeerDetailFragment)fragment).cancelChanges();
    }

    public void editMode(View view) {
        Fragment fragment = getFragmentManager().findFragmentByTag("DetailFragment");
        ((BeerDetailFragment)fragment).editMode(view.getId());
    }
}
