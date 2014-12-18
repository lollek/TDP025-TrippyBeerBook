package iix.se.trippybeerbook;

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
 *
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link BeerDetailFragment}.
 */
public class BeerDetailActivity extends Activity {
    boolean mCurrentItem;
    boolean mEditMode = false;

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
            mCurrentItem = mItemID == -1;
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
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, BeerListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mEditMode)
            cancelChanges(null);
        else
            NavUtils.navigateUpTo(this, new Intent(this, BeerListActivity.class));
    }

    /**
     * Save any modifications we have done
     * @param view Unused
     */
    public void saveChanges(View view) {
        mEditMode = false;
        getBeerDetailFragment().saveChanges();

        // If we're creating a new item, saving returns us to the BeerList
        if (mCurrentItem) {
            NavUtils.navigateUpTo(this, new Intent(this, BeerListActivity.class));
        }
    }

    /**
     * Cancel any modifications we have done.
     * @param view Unused
     */
    public void cancelChanges(View view) {
        mEditMode = false;
        getBeerDetailFragment().cancelChanges();
    }

    /**
     * Make a View editable
     * @param view View to edit
     */
    public void editMode(View view) {
        mEditMode = true;
        getBeerDetailFragment().editMode(view.getId());
    }

    /**
     * Helper function to avoid clutter
     * @return The attached BeerDetailFragment
     */
    BeerDetailFragment getBeerDetailFragment() {
        return (BeerDetailFragment)getFragmentManager().findFragmentByTag("DetailFragment");
    }
}
