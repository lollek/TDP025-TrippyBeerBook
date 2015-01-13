package iix.se.trippybeerbook;

import android.app.Activity;

import com.amazon.insights.*;

/**
 * Abstraction for easier use of AmazonInsights.
 * This will run as a singleton to avoid polling too much
 */
public class ABTest {
    private static ABTest mABTest;
    private final AmazonInsights mAmazonInsights;
    private boolean mAB_ColorfulButtons;
    private boolean mAB_ColorfulActionBar;
    private boolean mAB_ShowPercentage;

    /**
     * Return ABTest instance, and create one if necessary
     * @param activity activity
     * @return ABTest object
     */
    public static ABTest getInstance(Activity activity) {
        if (mABTest == null)
            mABTest = new ABTest(activity);
        return mABTest;
    }

    /**
     * This AB-test sets the "Add-new-beer" button to a big green button,
     * and shows two bit buttons (Save/Cancel) on the details screen.
     *
     * If it is not set, the action bar is shown (otherwise hidden), with a plus
     * for adding a beer, or on the details screen, a cancel and save button.
     *
     * Pros:
     *  Easier to find buttons, and clearer what they do
     *  Bright and friendly colors
     *
     * Cons:
     *  Not as clean look, may come off as childish
     *
     * @return True if we should place colorful buttons on the main screen,
     *         False if we should place them on the ActionBar (therefore quite grey)
     */
    public boolean colorfulButtons() { 
        return mAB_ColorfulButtons;
    }

    /**
     * This AB-test changes the color of the actionbar. It is a kind of compromise between
     * colorfulButtons (which is very colorful) and the default (which is a bit grey)
     * If colorfulButtons is true, this value has no effect.
     *
     * Pros:
     *  Looks a bit less grey and boring than the default
     *
     * @return True if the actionbar should be colored.
     *         False if it should be black/grey
     */
    public boolean colorfulActionBar() {
        return mAB_ColorfulActionBar;
    }

    /**
     * This AB-test adds the percentage of the beverage on the list view.
     *
     * Pros:
     *  More information readily available
     *
     * Cons:
     *  Clutters the screen
     *
     * @return True if we should show the percentage of the beverage
     *         False if the space should be left empty
     */
    public boolean showPercentage() {
        return mAB_ShowPercentage;
    }


    /**
     * Manually override ABTest-boolean. Useful to test that all test versions work
     * @param b New value of test
     */
    public void setColorfulButtons(boolean b) {
        mAB_ColorfulButtons = b;
    }
    public void setColorfulActionBar(boolean b) {
        mAB_ColorfulActionBar = b;
    }
    public void setShowPercentage(boolean b) {
        mAB_ShowPercentage = b;
    }


    /**
     * Record that an event has triggered.
     * @param event Name of the event
     */
    public void recordEvent(String event) {
        final EventClient eventClient = mAmazonInsights.getEventClient();
        eventClient.recordEvent(eventClient.createEvent(event));
    }
    /**
     * Send recorded events to Amazon.
     * This should run on onPause in each Activity which uses recordEvent
     */
    public void submitEvents() {
        mAmazonInsights.getEventClient().submitEvents();
    }
    
    /**
     * ABTest constructor
     * @param activity The activity to initialize the ABTest
     */
    private ABTest(Activity activity) {
        InsightsCredentials credentials = AmazonInsights.newCredentials(
                "2156346903af452c8e6e6af077a32899",
                "Z2gS+6/7h4vqBhKCuczMjXQ1wq/hK/Vwx5zFmA1D9AM=");
        mAmazonInsights = AmazonInsights.newInstance(credentials, activity.getApplicationContext());

        mAmazonInsights.getABTestClient()
                .getVariations("Visuals")
                .setCallback(new InsightsCallback<VariationSet>() {
                    @Override
                    public void onComplete(VariationSet variations) {
                        mAB_ColorfulButtons = variations
                                .getVariation("Visuals")
                                .getVariableAsBoolean("ColorfulButtons", true);
                        mAB_ColorfulActionBar = variations
                                .getVariation("Visuals")
                                .getVariableAsBoolean("ColorfulActionBar", true);
                        mAB_ShowPercentage = variations
                                .getVariation("Visuals")
                                .getVariableAsBoolean("ShowPercentage", true);
                    }
                });
    }
}
