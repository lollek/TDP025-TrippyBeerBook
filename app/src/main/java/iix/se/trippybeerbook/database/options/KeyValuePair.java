package iix.se.trippybeerbook.database.options;

import android.database.Cursor;

public class KeyValuePair {
    public long mID;
    public String mKey;
    public String mValue;

    public KeyValuePair(String key, String value) {
        mKey = key;
        mValue = value;
    }

    public KeyValuePair(Cursor cursor) {
        mID = cursor.getLong(0);
        mKey = cursor.getString(1);
        mValue = cursor.getString(2);
    }
}
