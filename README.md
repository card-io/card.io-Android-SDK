[![Build Status](https://travis-ci.org/card-io/card.io-Android-SDK.svg)](https://travis-ci.org/card-io/card.io-Android-SDK)

card.io SDK for Android
========================

[card.io](https://www.card.io/) provides fast, easy credit card scanning in mobile apps.

Stay up to date
---------------

Please be sure to keep your app up to date with the latest version of the SDK.
All releases follow [semantic versioning](http://semver.org/).

The latest version is available via `mavenCentral()`.  Just add the following dependency:

```
compile 'io.card:android-sdk:5.1.0'
```

You can receive updates about new versions via a few different channels:

* Follow [@cardio](https://twitter.com/cardio) (also great to send us feedback)
* Subscribe to our [card-io-sdk-announce](https://groups.google.com/forum/#!forum/card-io-sdk-announce) list.
* "Watch" this GitHub repository

Also be sure to check and post to the [Stack Overflow card.io tag](http://stackoverflow.com/questions/tagged/card.io).

Integration instructions
------------------------

The information in this guide is enough to get started. For additional details, see our **[javadoc](http://card-io.github.io/card.io-Android-SDK/)**.

*(Note: in the past, developers needed to sign up at the [card.io site](https://www.card.io) and obtain an* `app token`. *This is no longer required.)* 

### Requirements for card scanning

*   Rear-facing camera.
*   Android SDK version 8 (Android 2.2) or later.
*   ARMv7 processor.

A manual entry fallback mode is provided for devices that do not meet these requirements.

### Setup


##### If you use gradle, then add the following dependency from `mavenCentral()`:

```
compile 'io.card:android-sdk:5.1.0'
```

##### If you use something other than gradle, then:

1. Edit AndroidManifest.xml. We're going to add a few additional items in here:

    ```xml
    <uses-sdk android:minSdkVersion="8" />
    ```

2. Also in your `<manifest>` element, make sure the following permissions and features are present:

    ```xml
    <!-- Permission to vibrate - recommended, allows vibration feedback on scan -->
    <uses-permission android:name="android.permission.VIBRATE" />

    <!-- Permission to use camera - required -->
    <uses-permission android:name="android.permission.CAMERA" />

    <!-- Camera features - recommended -->
    <uses-feature android:name="android.hardware.camera" android:required="false" />
    <uses-feature android:name="android.hardware.camera.autofocus" android:required="false" />
    <uses-feature android:name="android.hardware.camera.flash" android:required="false" />
    ```

3. Within the `<application>` element, add activity entries:

    ```xml
    <!-- Activities responsible for gathering payment info -->
    <activity android:name="io.card.payment.CardIOActivity" android:configChanges="keyboardHidden|orientation" />
    <activity android:name="io.card.payment.DataEntryActivity" />
    ```

##### Note: Before you build in release mode, make sure to adjust your proguard configuration by adding the following to `proguard.cnf`:

```
-keep class io.card.**
-keepclassmembers class io.card.** {
    *;
}
```

### Sample code  (See the SampleApp for an example)

First, we'll assume that you're going to launch the scanner from a button,
and that you've set the button's `onClick` handler in the layout XML via `android:onClick="onScanPress"`.
Then, add the method as:

```java
public void onScanPress(View v) {
    Intent scanIntent = new Intent(this, CardIOActivity.class);

    // customize these values to suit your needs.
    scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true); // default: false
    scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false); // default: false
    scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false); // default: false

    // MY_SCAN_REQUEST_CODE is arbitrary and is only used within this activity.
    startActivityForResult(scanIntent, MY_SCAN_REQUEST_CODE);
}
```

Next, we'll override `onActivityResult()` to get the scan result.

```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == MY_SCAN_REQUEST_CODE) {
        String resultDisplayStr;
        if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
            CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);

            // Never log a raw card number. Avoid displaying it, but if necessary use getFormattedCardNumber()
            resultDisplayStr = "Card Number: " + scanResult.getRedactedCardNumber() + "\n";

            // Do something with the raw number, e.g.:
            // myService.setCardNumber( scanResult.cardNumber );

            if (scanResult.isExpiryValid()) {
                resultDisplayStr += "Expiration Date: " + scanResult.expiryMonth + "/" + scanResult.expiryYear + "\n";
            }

            if (scanResult.cvv != null) {
                // Never log or display a CVV
                resultDisplayStr += "CVV has " + scanResult.cvv.length() + " digits.\n";
            }

            if (scanResult.postalCode != null) {
                resultDisplayStr += "Postal Code: " + scanResult.postalCode + "\n";
            }
        }
        else {
            resultDisplayStr = "Scan was canceled.";
        }
        // do something with resultDisplayStr, maybe display it in a textView
        // resultTextView.setText(resultStr);
    }
    // else handle other activity results
}
```

### Hints &amp; Tips

* [Javadocs](http://card-io.github.io/card.io-Android-SDK/) are provided in this repo for a complete reference.
* card.io errors and warnings will be logged to the "card.io" tag.
* If upgrading the card.io SDK, first remove all card.io libraries so that you don't accidentally ship obsolete or unnecessary libraries. The bundled libraries may change.
* Processing images can be memory intensive.
    * [Memory Analysis for Android Applications](http://android-developers.blogspot.com/2011/03/memory-analysis-for-android.html) provides some useful information about how to track and reduce your app's memory useage.
* card.io recommends the use of [SSL pinning](http://blog.thoughtcrime.org/authenticity-is-broken-in-ssl-but-your-app-ha) when transmitting sensitive information to protect against man-in-the-middle attacks.
