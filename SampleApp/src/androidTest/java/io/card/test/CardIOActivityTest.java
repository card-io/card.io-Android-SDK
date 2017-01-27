package io.card.test;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;

import org.junit.Rule;
import org.junit.Test;

import java.lang.reflect.Field;

import io.card.payment.CardIOActivity;
import io.card.payment.CardScannerTester;
import io.card.payment.CreditCard;

import static com.lukekorth.deviceautomator.DeviceAutomator.onDevice;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CardIOActivityTest {

    private CardIOTestActivity mActivity;

    @Rule
    public final CustomActivityTestRule<CardIOTestActivity> mActivityTestRule =
            new CustomActivityTestRule<>(CardIOTestActivity.class, false, false);

    @Test(timeout = 30000)
    public void scansAmexCards() {
        CardScannerTester.setCardAsset("amex.png");

        startScan();

        waitForActivityToFinish();
        CreditCard result = getActivityResultIntent().getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);
        assertEquals("3743 260055 74998", result.getFormattedCardNumber());
    }

    private void startScan() {
        mActivityTestRule.launchActivity(null);
        mActivity = mActivityTestRule.getActivity();

        onDevice().acceptRuntimePermission(Manifest.permission.CAMERA);
    }

    private void waitForActivityToFinish() {
        long endTime = System.currentTimeMillis() + 10000;

        do {
            try {
                if (mActivity.isFinishing()) {
                    return;
                }
            } catch (Exception ignored) {}
        } while (System.currentTimeMillis() < endTime);

        throw new RuntimeException("Maximum wait elapsed (10s) while waiting for activity to finish");
    }

    private Intent getActivityResultIntent() {
        assertThat("Activity did not finish", mActivity.isFinishing(), is(true));

        try {
            Field resultDataField = Activity.class.getDeclaredField("mResultData");
            resultDataField.setAccessible(true);
            return (Intent) resultDataField.get(mActivity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
