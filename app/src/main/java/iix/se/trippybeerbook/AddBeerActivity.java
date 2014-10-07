package iix.se.trippybeerbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class AddBeerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_add);

        // Show the Up button in the action bar.
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            AddBeerFragment fragment = new AddBeerFragment();
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.beer_add_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, BeerListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addBeer(View view) {
        final String beer = ((EditText)findViewById(R.id.beerNameText)).getText().toString();
        final String brewery = ((EditText)findViewById(R.id.breweryNameText)).getText().toString();
        final String type = ((EditText)findViewById(R.id.beerTypeText)).getText().toString();
        final String country = ((EditText)findViewById(R.id.countryNameText)).getText().toString();
        float percentage;
        try {
            percentage = Float.parseFloat(((EditText) findViewById(R.id.countryNameText)).getText().toString());
        } catch (NumberFormatException e) {
            percentage = 0f;
        }
        final List<Pair<Integer, String>> list = new ArrayList<Pair<Integer, String>>();

        final BeerItem.BeerType item = new BeerItem.BeerType(beer, brewery, type, country, percentage, list);
        BeerItem.beerListQueue.add(item);
        NavUtils.navigateUpTo(this, new Intent(this, BeerListActivity.class));
    }
}