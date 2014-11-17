package iix.se.trippybeerbook;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import iix.se.trippybeerbook.database.Beer;
import iix.se.trippybeerbook.database.Database;


/**
 * A fragment representing a single Beer detail screen.
 * This fragment is either contained in a {@link BeerListActivity}
 * in two-pane mode (on tablets) or a {@link BeerDetailActivity}
 * on handsets.
 */
public class BeerDetailFragment extends Fragment {
    public static final String ARG_ITEM_ID = "item_id"; // Item ID to display
    private Beer mItem;                                 // Item to display

    // Mandatory empty constructor for screen orientation changes and stuff
    public BeerDetailFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            final long id = getArguments().getLong(ARG_ITEM_ID);
            if (id != -1) {
                mItem = new Database(getActivity().getApplicationContext()).getBeerById(id);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_beer_detail,
                container, false);

        // Editing an existing item
        if (mItem != null) {
            Float rating;
            try                 { rating = Float.parseFloat(mItem.mStars); }
            catch (Exception e) { rating = 0f; }

            final RatingBar stars = (RatingBar) rootView.findViewById(R.id.RatingBar);
            stars.setRating(rating);
            stars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                    mItem.mStars = Float.toString(v);
                    ((BeerDetailActivity)getActivity()).save(rootView);
                }
            });

            ((TextView) rootView.findViewById(R.id.beer_name)).setText(mItem.mName);
            ((TextView) rootView.findViewById(R.id.brewery_name)).setText(mItem.mBrewery);
            ((TextView) rootView.findViewById(R.id.beer_type)).setText(mItem.mBeerType);
            ((TextView) rootView.findViewById(R.id.country)).setText(mItem.mCountry);
            ((TextView) rootView.findViewById(R.id.percentage)).setText(mItem.mPercentage);

        // Creating a new item
        } else {
            rootView.findViewById(R.id.beer_name).setVisibility(View.GONE);
            rootView.findViewById(R.id.brewery_name).setVisibility(View.GONE);
            rootView.findViewById(R.id.beer_type).setVisibility(View.GONE);
            rootView.findViewById(R.id.country).setVisibility(View.GONE);
            rootView.findViewById(R.id.percentage).setVisibility(View.GONE);

            rootView.findViewById(R.id.beer_name_edit).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.brewery_name_edit).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.beer_type_edit).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.country_edit).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.percentage_edit).setVisibility(View.VISIBLE);

            rootView.findViewById(R.id.save_btn).setVisibility(View.VISIBLE);
        }

        return rootView;
    }
}
