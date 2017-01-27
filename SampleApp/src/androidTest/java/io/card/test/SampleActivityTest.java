package io.card.test;

import android.Manifest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import io.card.development.R;
import io.card.development.SampleActivity;
import io.card.payment.i18n.LocalizedStrings;
import io.card.payment.i18n.StringKey;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.lukekorth.deviceautomator.DeviceAutomator.onDevice;
import static org.hamcrest.Matchers.containsString;

public class SampleActivityTest {

    @Rule
    public final CustomActivityTestRule<SampleActivity> mActivityTestRule =
            new CustomActivityTestRule<>(SampleActivity.class);

    @Before
    public void setup() {
        mActivityTestRule.getActivity();
    }

    @Test
    public void cancelInManualEntryExistsActivity() {
        onView(withText("Force keyboard entry (bypass scan)")).perform(click());
        onView(withText("Scan Credit Card using Card.io")).perform(click());
        onView(withText("Card Number")).check(matches(isDisplayed()));

        onView(withText(LocalizedStrings.getString(StringKey.CANCEL))).perform(click());

        onView(withText("Force keyboard entry (bypass scan)")).check(matches(isDisplayed()));
    }

    @Test
    public void manualEntryReturnsCardData() {
        onView(withText("Expiry")).perform(click());
        onView(withText("CVV")).perform(click());
        onView(withText("Postal Code")).perform(click());
        onView(withText("Cardholder Name")).perform(click());
        onView(withText("Force keyboard entry (bypass scan)")).perform(click());
        onView(withText("Scan Credit Card using Card.io")).perform(click());

        fillInCardForm();
        onView(withText(LocalizedStrings.getString(StringKey.DONE))).perform(click());

        onView(withId(R.id.result)).check(matches(withText(containsString("1111"))));
        onView(withId(R.id.result)).check(matches(withText(containsString("Expiry: 12/2022"))));
        onView(withId(R.id.result)).check(matches(withText(containsString("CVV: 123"))));
        onView(withId(R.id.result)).check(matches(withText(containsString("Postal Code: 95131"))));
        onView(withId(R.id.result)).check(matches(withText(containsString("Cardholder Name: John Doe"))));
    }

    @Test
    public void canEnterManualEntryFromScanActivity() {
        onView(withText("Expiry")).perform(click());
        onView(withText("CVV")).perform(click());
        onView(withText("Postal Code")).perform(click());
        onView(withText("Cardholder Name")).perform(click());
        onView(withText("Scan Credit Card using Card.io")).perform(click());

        onDevice().acceptRuntimePermission(Manifest.permission.CAMERA);

        onView(withText(LocalizedStrings.getString(StringKey.KEYBOARD))).check(matches(isDisplayed()));
        onView(withText(LocalizedStrings.getString(StringKey.KEYBOARD))).perform(click());

        fillInCardForm();
        onView(withText(LocalizedStrings.getString(StringKey.DONE))).perform(click());
    }

    private void fillInCardForm() {
        onView(withId(100)).perform(click(), typeText("4111111111111111"));
        onView(withId(101)).perform(click(), typeText("1222"));
        onView(withId(102)).perform(click(), typeText("123"));
        onView(withId(103)).perform(click(), typeText("95131"));
        onView(withId(104)).perform(click(), typeText("John Doe"));
    }
}
