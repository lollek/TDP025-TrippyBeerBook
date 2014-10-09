package iix.se.trippybeerbook;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A fragment representing a single Beer detail screen.
 * This fragment is either contained in a {@link BeerListActivity}
 * in two-pane mode (on tablets) or a {@link BeerDetailActivity}
 * on handsets.
 */
public class BeerDetailFragment extends Fragment {
    public static final String ARG_ITEM_ID = "item_id"; // Item ID to display
    private BeerItem.BeerType mItem;                    // Item to display

    // Mandatory empty constructor for screen orientation changes and stuff
    public BeerDetailFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = BeerItem.beerList.get(getArguments().getInt(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_beer_detail,
                container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.beer_name)).setText(mItem.mName);
            ((TextView) rootView.findViewById(R.id.brewery_name)).setText(mItem.mBrewery);
            ((TextView) rootView.findViewById(R.id.beer_type)).setText(mItem.mBeerType);
            ((TextView) rootView.findViewById(R.id.country)).setText(mItem.mCountry);
            ((TextView) rootView.findViewById(R.id.percentage)).setText(mItem.mPercentage);
        }

        return rootView;
    }
}
