package iix.se.trippybeerbook.database.beer;

import android.database.Cursor;

public class Beer {
    public long mID;
    public final String mName;
    public final String mBrewery;
    public final String mBeerType;
    public final String mCountry;
    public final String mPercentage;
    public String mStars;
    public final String mComment;

    public Beer(String name, String brewery, String beerType,
                String country, String percentage, String stars,
                String comment) {
        mName = name;
        mBrewery = brewery;
        mBeerType = beerType;
        mCountry = country;
        mPercentage = percentage;
        mStars = stars;
        mComment = comment;
    }

    public Beer(Cursor cursor) {
        mID = cursor.getLong(0);
        mName = cursor.getString(1);
        mBrewery = cursor.getString(2);
        mBeerType = cursor.getString(3);
        mCountry = cursor.getString(4);
        mPercentage = cursor.getString(5);
        mStars = cursor.getString(6);
        mComment = cursor.getString(7);
    }

    public String toString() {
        return mName;
    }

}