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
    private boolean mAB_AddButtonInList;

    public static ABTest getInstance(Activity activity) {
        if (mABTest == null)
            mABTest = new ABTest(activity);
        return mABTest;
    }
    public boolean addButtonInList() { return mAB_AddButtonInList; }

    private ABTest(Activity activity) {
        InsightsCredentials credentials = AmazonInsights.newCredentials(
                "2156346903af452c8e6e6af077a32899",
                "Z2gS+6/7h4vqBhKCuczMjXQ1wq/hK/Vwx5zFmA1D9AM=");
        mAmazonInsights = AmazonInsights.newInstance(credentials, activity.getApplicationContext());

        mAmazonInsights.getABTestClient()
                .getVariations("Modify Add Button")
                .setCallback(new InsightsCallback<VariationSet>() {
                    @Override
                    public void onComplete(VariationSet variations) {
                        mAB_AddButtonInList = variations
                                .getVariation("Modify Add Button")
                                .getVariableAsBoolean("AddButtonInList", true);
                    }
                });
    }
}
