package iix.se.trippybeerbook;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
    public static final String ARG_ITEM_ID = "item_id";
    private Database mDatabase;
    private Beer mItem;

    /* Mandatory empty constructor for screen orientation changes and stuff */
    public BeerDetailFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            final long id = getArguments().getLong(ARG_ITEM_ID);
            if (id != -1)
                mItem = new Database(getActivity().getApplicationContext()).getBeerById(id);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle _unused) {
        final View rootView = inflater.inflate(R.layout.fragment_beer_detail,
                container, false);
        final ABTest mABTest = ABTest.getInstance(getActivity());

        if (mABTest.colorfulButtons()) {
            rootView.findViewById(R.id.save_btn).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.cancel_btn).setVisibility(View.VISIBLE);
        }

        if (mItem != null)
            onCreateViewExisting(rootView);
        else
            onCreateViewNew(rootView);

        return rootView;
    }

    /**
     * onCreateView for editing or viewing an item
     * @param view The root view
     */
    void onCreateViewExisting(final View view) {
        final RatingBar stars = (RatingBar) view.findViewById(R.id.RatingBar);
        stars.setRating(Float.parseFloat(mItem.mStars));
        stars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                mItem.mStars = Float.toString(v);
                saveChanges();
            }
        });

        ((TextView) view.findViewById(R.id.beer_name)).setText(mItem.mName);
        ((TextView) view.findViewById(R.id.brewery_name)).setText(mItem.mBrewery);
        ((TextView) view.findViewById(R.id.beer_type)).setText(mItem.mBeerType);
        ((TextView) view.findViewById(R.id.country)).setText(mItem.mCountry);
        ((TextView) view.findViewById(R.id.percentage)).setText(mItem.mPercentage);
        ((TextView) view.findViewById(R.id.beer_comment)).setText(mItem.mComment);
    }

    /**
     * onCreateview for new items
     * @param view The root view
     */
    void onCreateViewNew(final View view) {
        view.findViewById(R.id.beer_name).setVisibility(View.GONE);
        view.findViewById(R.id.brewery_name).setVisibility(View.GONE);
        view.findViewById(R.id.beer_type).setVisibility(View.GONE);
        view.findViewById(R.id.country).setVisibility(View.GONE);
        view.findViewById(R.id.percentage).setVisibility(View.GONE);
        view.findViewById(R.id.beer_comment).setVisibility(View.GONE);

        view.findViewById(R.id.beer_name_edit).setVisibility(View.VISIBLE);
        view.findViewById(R.id.brewery_name_edit).setVisibility(View.VISIBLE);
        view.findViewById(R.id.beer_type_edit).setVisibility(View.VISIBLE);
        view.findViewById(R.id.country_edit).setVisibility(View.VISIBLE);
        view.findViewById(R.id.percentage_edit).setVisibility(View.VISIBLE);
        view.findViewById(R.id.beer_comment_edit).setVisibility(View.VISIBLE);
    }

    /**
     * Unset any changes made and return to "view mode"
     */
    void cancelChanges() {
        disableEditMode(R.id.beer_name, R.id.beer_name_edit, false);
        disableEditMode(R.id.beer_type, R.id.beer_type_edit, false);
        disableEditMode(R.id.brewery_name, R.id.brewery_name_edit, false);
        disableEditMode(R.id.country, R.id.country_edit, false);
        disableEditMode(R.id.percentage, R.id.percentage_edit, false);
        disableEditMode(R.id.beer_comment, R.id.beer_comment_edit, false);
    }

    /**
     * Save any changes made and return to "view mode"
     */
    void saveChanges() {
        disableEditMode(R.id.beer_name, R.id.beer_name_edit, true);
        disableEditMode(R.id.beer_type, R.id.beer_type_edit, true);
        disableEditMode(R.id.brewery_name, R.id.brewery_name_edit, true);
        disableEditMode(R.id.country, R.id.country_edit, true);
        disableEditMode(R.id.percentage, R.id.percentage_edit, true);
        disableEditMode(R.id.beer_comment, R.id.beer_comment_edit, true);

        final Activity activity = getActivity();
        if (mDatabase == null) {
            mDatabase = new Database(activity);
        }

        Beer item = new Beer(
                ((TextView)activity.findViewById(R.id.beer_name)).getText().toString(),
                ((TextView)activity.findViewById(R.id.brewery_name)).getText().toString(),
                ((TextView)activity.findViewById(R.id.beer_type)).getText().toString(),
                ((TextView)activity.findViewById(R.id.country)).getText().toString(),
                ((TextView)activity.findViewById(R.id.percentage)).getText().toString(),
                Float.toString(((RatingBar)activity.findViewById(R.id.RatingBar)).getRating()),
                ((TextView)activity.findViewById(R.id.beer_comment)).getText().toString());

        if (mItem != null) {
            item.mID = mItem.mID;
            mDatabase.updateBeer(item);
        } else {
            mDatabase.addBeer(item);
        }
    }

    /**
     * Helper function to enter edit mode for one field
     * @param readID ID of the TextView
     * @param writeID ID of the EditView
     */
    void activateEditMode(int readID, int writeID) {
        final Activity activity = getActivity();
        final TextView readText = (TextView) activity.findViewById(readID);
        final EditText writeText = (EditText) activity.findViewById(writeID);

        writeText.setText(readText.getText());

        readText.setVisibility(View.GONE);
        writeText.setVisibility(View.VISIBLE);
    }

    /**
     * Helper function to exit edit mode for one field
     * @param readID ID of the TextView
     * @param writeID ID of the EditView
     * @param saveChangedData Should we copy the changed data to the TextView?
     */
    void disableEditMode(int readID, int writeID, boolean saveChangedData) {
        final Activity activity = getActivity();
        final EditText writeText = (EditText) activity.findViewById(writeID);

        if (writeText.getVisibility() == View.VISIBLE) {
            final TextView readText = (TextView) activity.findViewById(readID);
            if (saveChangedData)
                readText.setText(writeText.getText());
            readText.setVisibility(View.VISIBLE);
            writeText.setVisibility(View.GONE);
        }
    }

    /**
     * Helper function for activateEditMode
     * @param id ID of the TextView to edit
     */
    void editMode(int id) {
        switch (id) {
            case R.id.beer_name: activateEditMode(id, R.id.beer_name_edit); break;
            case R.id.brewery_name: activateEditMode(id, R.id.brewery_name_edit); break;
            case R.id.beer_type: activateEditMode(id, R.id.beer_type_edit); break;
            case R.id.country: activateEditMode(id, R.id.country_edit); break;
            case R.id.percentage: activateEditMode(id, R.id.percentage_edit); break;
            case R.id.beer_comment: activateEditMode(id, R.id.beer_comment_edit); break;
        }
    }
}
