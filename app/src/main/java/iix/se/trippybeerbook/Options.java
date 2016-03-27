package iix.se.trippybeerbook;

import android.content.Context;

import iix.se.trippybeerbook.database.options.Database;

public class Options {
    private Database mDatabase;
    private boolean mColorful;

    private static final String COLORFULNESS = "colorful";

    public Options (Context context) {
        mDatabase = new Database(context);
        mColorful = Boolean.parseBoolean(mDatabase.getValue(COLORFULNESS));
    }

    /**
     * Set option colorfulness
     * @param bool New value
     */
    public void setColorfulness(boolean bool) {
        mColorful = bool;
        mDatabase.setValue(COLORFULNESS, String.valueOf(mColorful));
    }

    /**
     * Get option colorfulness
     * @return The current colorfulness value
     */
    public boolean getColorfulness() {
        return mColorful;
    }
}
