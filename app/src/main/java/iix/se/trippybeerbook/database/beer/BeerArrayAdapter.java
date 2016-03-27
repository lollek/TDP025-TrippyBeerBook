package iix.se.trippybeerbook.database.beer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import iix.se.trippybeerbook.R;
import iix.se.trippybeerbook.database.beer.Beer;

public class BeerArrayAdapter extends ArrayAdapter<Beer> {
    private final Context mContext;
    private final List<Beer> mList;
    private int mLayoutId = -1;

    public BeerArrayAdapter(Context context, List<Beer> objects) {
        super(context, R.layout.beer_list_item_percentage, objects);
        mContext = context;
        mList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (mLayoutId == -1) {
            mLayoutId = R.layout.beer_list_item_percentage;
        }

        View view = convertView;
        if (view == null) {
            view = ((Activity) mContext).getLayoutInflater().inflate(mLayoutId, parent, false);
        }

        final TextView text1 = (TextView)view.findViewById(R.id.text1);
        final TextView text2 = (TextView)view.findViewById(R.id.text2);
        final TextView stars = (TextView)view.findViewById(R.id.stars);
        final Beer item = mList.get(position);

        text1.setText(item.mName);
        text2.setText(item.mBrewery);
        stars.setText(item.mStars.substring(0, 1));
        final GradientDrawable shape = (GradientDrawable) stars.getBackground();

        switch((int)Float.parseFloat(item.mStars)) {
            case 1: /* Dark orange */
                shape.setColor(Color.rgb(255, 136,  0));
                break;
            case 2: /* Light orange */
                shape.setColor(Color.rgb(255, 187, 51));
                break;
            case 3: /* Yellow */
                shape.setColor(Color.YELLOW);
                break;
            case 4: /* Light green */
                shape.setColor(Color.rgb(153, 204,  0));
                break;
            case 5:/* Dark green */
                shape.setColor(Color.rgb(102, 153,  0));
                break;
            default: /* No score = white "?" */
                shape.setColor(Color.WHITE);
                stars.setText("?");
                break;
        }

        if (mLayoutId == R.layout.beer_list_item_percentage) {
            final TextView percentage = (TextView)view.findViewById(R.id.listed_percentage);
            percentage.setText(item.mPercentage + "%");
        }

        return view;
    }
}
