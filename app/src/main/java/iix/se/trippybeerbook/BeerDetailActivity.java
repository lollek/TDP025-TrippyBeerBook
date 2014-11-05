package iix.se.trippybeerbook;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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
    boolean mModifying = false;

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
            arguments.putLong(BeerDetailFragment.ARG_ITEM_ID,
                    getIntent().getLongExtra(BeerDetailFragment.ARG_ITEM_ID, 0));
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

    public void modify(View view) {
        final int viewMod = mModifying ? View.VISIBLE : View.GONE;
        final int editMod = mModifying ? View.GONE : View.VISIBLE;

        final List<Integer> viewIDs = new ArrayList<Integer>();
        viewIDs.add(R.id.beer_name);
        viewIDs.add(R.id.beer_type);
        viewIDs.add(R.id.brewery_name);
        viewIDs.add(R.id.country);
        viewIDs.add(R.id.percentage);

        final List<Integer> editIDs = new ArrayList<Integer>();
        editIDs.add(R.id.beer_name_edit);
        editIDs.add(R.id.beer_type_edit);
        editIDs.add(R.id.brewery_name_edit);
        editIDs.add(R.id.country_edit);
        editIDs.add(R.id.percentage_edit);

        for (int i = 0; i < viewIDs.size(); ++i) {
            TextView viewable = (TextView) findViewById(viewIDs.get(i));
            viewable.setVisibility(viewMod);

            EditText editable = (EditText) findViewById(editIDs.get(i));
            editable.setVisibility(editMod);

            if (viewable.getText() != editable.getText()) {
                if (mModifying) {
                    viewable.setText(editable.getText());
                } else {
                    editable.setText(viewable.getText());
                }
            }
        }

        ((Button)findViewById(R.id.do_modify))
                .setText(getString(mModifying ? R.string.text_modify : R.string.text_modify_done));

        mModifying = !mModifying;
    }
}
