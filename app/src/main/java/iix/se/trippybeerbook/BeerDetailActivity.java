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
    private boolean mEditMode = false;
    private boolean mCurrentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_detail);

        final ActionBar actionBar = getActionBar();
        // Show the Up button in the action bar.
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (actionBar != null) {
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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_detail, menu);
        return true;
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
        if (mEditMode) {
            cancelChanges(null);
        } else {
            NavUtils.navigateUpTo(this, new Intent(this, BeerListActivity.class));
        }
    }

    /**
     * Save any modifications we have done
     * @param _unused Unused
     */
    public void saveChanges(@SuppressWarnings("UnusedParameters") View _unused) {
        getBeerDetailFragment().saveChanges();
        if (mCurrentItem || !mEditMode) {
            NavUtils.navigateUpTo(this, new Intent(this, BeerListActivity.class));
        } else {
            mEditMode = false;
        }
    }

    /**
     * Cancel any modifications we have done.
     * @param _unused Unused
     */
    public void cancelChanges(@SuppressWarnings("UnusedParameters") View _unused) {
        if (mCurrentItem || !mEditMode) {
            NavUtils.navigateUpTo(this, new Intent(this, BeerListActivity.class));
        } else {
            getBeerDetailFragment().cancelChanges();
            mEditMode = false;
        }
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
