package iix.se.trippybeerbook;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuInflater;
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
    private boolean mCurrentItem;
    private ABTest mABTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_detail);
        if (mABTest == null)
            mABTest = ABTest.getInstance(this);

        final ActionBar actionBar = getActionBar();
        // Show the Up button in the action bar.
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        if (mABTest.colorfulButtons()) {
            if (actionBar != null)
                actionBar.hide();
        } else if (mABTest.colorfulActionBar()) {
            if (actionBar != null)
                actionBar.setBackgroundDrawable(new ColorDrawable((Color.parseColor("#669900"))));
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
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mABTest.colorfulButtons()) {
            return super.onCreateOptionsMenu(menu);
        } else {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.actionbar_detail, menu);
            return true;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpTo(this, new Intent(this, BeerListActivity.class));
                return true;
            case R.id.action_bar_cancel:
                cancelChanges(null);
                return true;
            case R.id.action_bar_save:
                saveChanges(null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        NavUtils.navigateUpTo(this, new Intent(this, BeerListActivity.class));
    }

    /**
     * Save any modifications we have done
     * @param _unused Unused
     */
    public void saveChanges(View _unused) {
        getBeerDetailFragment().saveChanges();
        NavUtils.navigateUpTo(this, new Intent(this, BeerListActivity.class));
    }

    /**
     * Cancel any modifications we have done.
     * @param view Unused
     */
    public void cancelChanges(View view) {
        // If we're creating a new item, saving returns us to the BeerList
        if (!mCurrentItem) {
            getBeerDetailFragment().cancelChanges();
        }
        NavUtils.navigateUpTo(this, new Intent(this, BeerListActivity.class));
    }

    /**
     * Make a View editable
     * @param view View to edit
     */
    public void editMode(View view) {
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
