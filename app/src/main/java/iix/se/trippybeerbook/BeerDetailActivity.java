package iix.se.trippybeerbook;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import iix.se.trippybeerbook.database.Beer;
import iix.se.trippybeerbook.database.Database;

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
    Database mDatabase;
    long mItemID;

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
            mItemID = getIntent().getLongExtra(BeerDetailFragment.ARG_ITEM_ID, 0);
            arguments.putLong(BeerDetailFragment.ARG_ITEM_ID, mItemID);
            BeerDetailFragment fragment = new BeerDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .add(R.id.beer_detail_container, fragment)
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

    public void cancel(View view) {
        unmodifyText(R.id.beer_name, R.id.beer_name_edit, false);
        unmodifyText(R.id.beer_type, R.id.beer_type_edit, false);
        unmodifyText(R.id.brewery_name, R.id.brewery_name_edit, false);
        unmodifyText(R.id.country, R.id.country_edit, false);
        unmodifyText(R.id.percentage, R.id.percentage_edit, false);
        findViewById(R.id.save_btn).setVisibility(View.GONE);
        findViewById(R.id.cancel_btn).setVisibility(View.GONE);
    }

    public void save(View view) {
        unmodifyText(R.id.beer_name, R.id.beer_name_edit, true);
        unmodifyText(R.id.beer_type, R.id.beer_type_edit, true);
        unmodifyText(R.id.brewery_name, R.id.brewery_name_edit, true);
        unmodifyText(R.id.country, R.id.country_edit, true);
        unmodifyText(R.id.percentage, R.id.percentage_edit, true);
        findViewById(R.id.save_btn).setVisibility(View.GONE);
        findViewById(R.id.cancel_btn).setVisibility(View.GONE);
        if (mDatabase == null) {
            mDatabase = new Database(this);
        }
        Beer item = new Beer(
                ((TextView)findViewById(R.id.beer_name)).getText().toString(),
                ((TextView)findViewById(R.id.brewery_name)).getText().toString(),
                ((TextView)findViewById(R.id.beer_type)).getText().toString(),
                ((TextView)findViewById(R.id.country)).getText().toString(),
                ((TextView)findViewById(R.id.percentage)).getText().toString());
        if (mItemID != -1) {
            item.mID = mItemID;
            mDatabase.updateBeer(item);
        } else {
            mDatabase.addBeer(item);
            NavUtils.navigateUpTo(this, new Intent(this, BeerListActivity.class));
        }
    }

    private void unmodifyText(int roId, int rwId, boolean save) {
        final EditText rwText = (EditText) findViewById(rwId);
        if (rwText.getVisibility() == View.VISIBLE) {
            final TextView roText = (TextView) findViewById(roId);
            if (save) {
                roText.setText(rwText.getText());
            }
            roText.setVisibility(View.VISIBLE);
            rwText.setVisibility(View.GONE);
        }
    }

    private void modifyText(int roId, int rwId) {
        findViewById(R.id.save_btn).setVisibility(View.VISIBLE);
        findViewById(R.id.cancel_btn).setVisibility(View.VISIBLE);
        final TextView roText = (TextView) findViewById(roId);
        final EditText rwText = (EditText) findViewById(rwId);

        rwText.setText(roText.getText());

        roText.setVisibility(View.GONE);
        rwText.setVisibility(View.VISIBLE);
    }
    public void modifyBeerName(View view) { modifyText(R.id.beer_name, R.id.beer_name_edit); }
    public void modifyBeerType(View view) { modifyText(R.id.beer_type, R.id.beer_type_edit); }
    public void modifyBrewery(View view) { modifyText(R.id.brewery_name, R.id.brewery_name_edit); }
    public void modifyCountry(View view) { modifyText(R.id.country, R.id.country_edit); }
    public void modifyPercentage(View view) { modifyText(R.id.percentage, R.id.percentage_edit); }
}
