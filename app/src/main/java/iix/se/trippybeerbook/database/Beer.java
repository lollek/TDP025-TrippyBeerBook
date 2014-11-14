package iix.se.trippybeerbook.database;

import android.database.Cursor;

public class Beer {
    public long mID;
    public String mName;
    public String mBrewery;
    public String mBeerType;
    public String mCountry;
    public String mPercentage;
    public String mStars;
    public String mComment;

    public Beer(String name, String brewery, String beerType,
                String country, String percentage, String stars) {
        mName = name;
        mBrewery = brewery;
        mBeerType = beerType;
        mCountry = country;
        mPercentage = percentage;
        mStars = stars;
        mComment = "";
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