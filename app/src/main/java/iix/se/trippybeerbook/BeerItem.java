package iix.se.trippybeerbook;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class BeerItem {
    public static final List<BeerType> beerList = new ArrayList<BeerType>();
    public static final List<BeerType> beerListQ = new ArrayList<BeerType>();

    static { /* TODO: Remove these placeholders */
        Pair<Integer, String> olle = new Pair<Integer, String>(4, "olle");
        Pair<Integer, String> sofie = new Pair<Integer, String>(4, "sofie");
        List<Pair<Integer, String>> list = new ArrayList<Pair<Integer, String>>();
        list.add(olle);
        list.add(sofie);

        addItem(new BeerType("Punk IPA", "BrewDog", "IPA", "Scotland", "5.6", list));
        addItem(new BeerType("Five AM Saint", "BrewDog", "Amber Ale", "Scotland", "5", list));
        addItem(new BeerType("Rochefort 10", "Brasserie Rochefort", "Trappist", "Belgium", "11.3", list));
    }

    private static void addItem(BeerType item) {
        beerList.add(item);
    }

    public static class BeerType {
        public final String mName;
        public final String mBrewery;
        public final String mBeerType;
        public final String mCountry;
        public final String mPercentage;
        public final List<Pair<Integer, String>> mPoints;

        public BeerType(String name, String brewery, String beerType,
                        String country, String percentage,
                        List<Pair<Integer, String>> points) {
            mName = name;
            mBrewery = brewery;
            mBeerType = beerType;
            mCountry = country;
            mPercentage = percentage;
            mPoints = points;
        }

        public String toString() {
            return mName;
        }

    }
}
