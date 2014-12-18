package iix.se.trippybeerbook;

import android.app.Activity;

import com.amazon.insights.*;

/**
 * Abstraction for easier use of AmazonInsights.
 * This will run as a singleton to avoid polling too much
 */
public class ABTest {
    private static ABTest mABTest;
    private AmazonInsights mAmazonInsights;
    private boolean mAB_ButtonsOnViewScreen;

    /**
     * Returns the ABTest instance currently running, or creating one if there is none.
     * @param activity The activity to use the ABTest class
     */
    public static ABTest getInstance(Activity activity) {
        if (mABTest == null)
            mABTest = new ABTest(activity);
        return mABTest;
    }

    /**
     * AB Test for placing buttons
     * @return True if we should place buttons on the main screen, False if we should place them on the ActionBar.
     */
    public boolean buttonsOnViewScreen() {
      return mAB_ButtonsOnViewScreen;
    }

    /**
     * Record something the user has done
     * @param event What the user has done
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
                .getVariations("ButtonLocation")
                .setCallback(new InsightsCallback<VariationSet>() {
                    @Override
                    public void onComplete(VariationSet variations) {
                        mAB_ButtonsOnViewScreen = variations
                                .getVariation("ButtonLocation")
                                .getVariableAsBoolean("AddButtonInList", false);
                    }
                });
    }
}
