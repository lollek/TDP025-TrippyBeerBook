package iix.se.trippybeerbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import iix.se.trippybeerbook.database.Beer;
import iix.se.trippybeerbook.database.Database;

public class AddBeerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_add);

        // Show the Up button in the action bar.
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Add fragment if we don't already have one
        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.beer_add_container, new AddBeerFragment())
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

    public void addBeer(View view) {
        final String beer = stringFromTextField(R.id.beerNameText);
        final String brewery = stringFromTextField(R.id.breweryNameText);
        final String type = stringFromTextField(R.id.beerTypeText);
        final String country = stringFromTextField(R.id.countryNameText);
        final String percentage = stringFromTextField(R.id.percentageText);

        final Beer item = new Beer(beer, brewery, type, country, percentage);
        new Database(getApplicationContext()).addBeer(item);
        NavUtils.navigateUpTo(this, new Intent(this, BeerListActivity.class));
    }

    private String stringFromTextField(int id) {
        try {
            return ((EditText) findViewById(id)).getText().toString();
        } catch (NullPointerException e) {
            return "";
        }
    }
}