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

import java.util.NoSuchElementException;

import iix.se.trippybeerbook.database.beer.Beer;
import iix.se.trippybeerbook.database.beer.Database;


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
    private int mPrevEditedField = -1;

    /* Mandatory empty constructor for screen orientation changes and stuff */
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle _unused) {
        final View rootView = inflater.inflate(R.layout.fragment_beer_detail,
            container, false);

        return mItem == null ? onCreateViewNew(rootView) : onCreateViewExisting(rootView);
    }

    /**
     * onCreateView for editing or viewing an item
     * @param view The root view
     * @return The provided view
     */
    View onCreateViewExisting(final View view) {
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
        ((TextView) view.findViewById(R.id.percentage)).setText(mItem.mPercentage + "%");
        ((TextView) view.findViewById(R.id.beer_comment)).setText(mItem.mComment);

        return view;
    }

    /**
     * onCreateview for new items
     * @param view The root view
     * @return The provided view
     */
    View onCreateViewNew(final View view) {
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

        return view;
    }

    /**
     * Unset any changes made and return to "view mode"
     */
    void cancelChanges() {
        disableEditMode(R.id.beer_name, false);
        disableEditMode(R.id.beer_type, false);
        disableEditMode(R.id.brewery_name, false);
        disableEditMode(R.id.country, false);
        disableEditMode(R.id.percentage, false);
        disableEditMode(R.id.beer_comment, false);
    }

    /**
     * Save any changes made and return to "view mode"
     */
    void saveChanges() {
        disableEditMode(R.id.beer_name, true);
        disableEditMode(R.id.beer_type, true);
        disableEditMode(R.id.brewery_name, true);
        disableEditMode(R.id.country, true);
        disableEditMode(R.id.percentage, true);
        disableEditMode(R.id.beer_comment, true);

        final Activity activity = getActivity();
        if (mDatabase == null) {
            mDatabase = new Database(activity);
        }

        Beer item = new Beer(
            getTextFromView(((TextView)activity.findViewById(R.id.beer_name))).toString(),
            getTextFromView(((TextView)activity.findViewById(R.id.brewery_name))).toString(),
            getTextFromView(((TextView)activity.findViewById(R.id.beer_type))).toString(),
            getTextFromView(((TextView)activity.findViewById(R.id.country))).toString(),
            getTextFromView(((TextView)activity.findViewById(R.id.percentage))).toString(),
            Float.toString(((RatingBar)activity.findViewById(R.id.RatingBar)).getRating()),
            getTextFromView(((TextView)activity.findViewById(R.id.beer_comment))).toString());

        if (mItem != null) {
            item.mID = mItem.mID;
            mDatabase.updateBeer(item);
        } else {
            mDatabase.addBeer(item);
        }
    }

    /**
     * Helper function to enter edit mode for one field
     * @param id ID of the TextView to activate edit mode for
     */
    void activateEditMode(int id) {
        final Activity activity = getActivity();
        final TextView textView = (TextView) activity.findViewById(id);
        final EditText editText = getEditTextforId(id);

        editText.setText(getTextFromView(textView));

        textView.setVisibility(View.GONE);
        editText.setVisibility(View.VISIBLE);
    }

    /**
     * Helper function to exit edit mode for one field
     * @param id ID of the TextView to disable edit mode for
     * @param saveChangedData Should we copy the changed data to the TextView?
     */
    void disableEditMode(int id, boolean saveChangedData) {
        final Activity activity = getActivity();
        final EditText editText = getEditTextforId(id);

        if (editText.getVisibility() != View.VISIBLE) {
            return;
        }

        final TextView textView = (TextView) activity.findViewById(id);
        if (saveChangedData) {
            textView.setText(getTextFromView(editText));
        }

        textView.setVisibility(View.VISIBLE);
        editText.setVisibility(View.GONE);
    }

    CharSequence getTextFromView(EditText editText) {
        return editText.getId() == R.id.percentage
            ? editText.getText() + "%"
            : editText.getText().toString();
    }
    CharSequence getTextFromView(TextView textView) {
        final CharSequence charSequence = textView.getText();
        if (textView.getId() == R.id.percentage && charSequence.length() > 0 &&
            charSequence.charAt(charSequence.length() -1) == '%') {
            return textView.getText().subSequence(0, textView.getText().length() - 1);
        }
        return charSequence;
    }

    EditText getEditTextforId(int id) {
        final Activity activity = getActivity();
        switch (id) {
            case R.id.beer_name:
                return (EditText) activity.findViewById(R.id.beer_name_edit);
            case R.id.brewery_name:
                return (EditText) activity.findViewById(R.id.brewery_name_edit);
            case R.id.beer_type:
                return (EditText) activity.findViewById(R.id.beer_type_edit);
            case R.id.country:
                return (EditText) activity.findViewById(R.id.country_edit);
            case R.id.percentage:
                return (EditText) activity.findViewById(R.id.percentage_edit);
            case R.id.beer_comment:
                return (EditText) activity.findViewById(R.id.beer_comment_edit);
            default:
                throw new NoSuchElementException();
        }
    }

    /**
     * Helper function for activateEditMode to keep a stable API
     * @param id ID of the TextView to edit
     */
    void editMode(int id) {
        if (mPrevEditedField != -1) {
            disableEditMode(mPrevEditedField, true);
        }
        mPrevEditedField = id;
        activateEditMode(id);
    }
}
