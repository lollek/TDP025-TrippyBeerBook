package iix.se.trippybeerbook.database;

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

public class BeerArrayAdapter extends ArrayAdapter<Beer> {
    static int LAYOUT = R.layout.beer_list_item;
    Context mContext;
    List<Beer> mList;

    public BeerArrayAdapter(Context context, List<Beer> objects) {
        super(context, LAYOUT, objects);
        mContext = context;
        mList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = ((Activity)mContext).getLayoutInflater().inflate(LAYOUT, parent, false);
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
            case 1: shape.setColor(Color.rgb(255, 136,  0)); break;  /* Dark orange */
            case 2: shape.setColor(Color.rgb(255, 187, 51)); break;  /* Light orange */
            case 3: shape.setColor(Color.YELLOW); break;             /* Yellow */
            case 4: shape.setColor(Color.rgb(153, 204,  0)); break;  /* Light green */
            case 5: shape.setColor(Color.rgb(102, 153,  0)); break;  /* Dark green */
            default:                                                 /* No score = white "?" */
                shape.setColor(Color.WHITE);
                stars.setText("?");
                break;
        }
        return view;
    }
}
