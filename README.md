card.io SDK for Android
========================

[card.io](https://www.card.io/) provides fast, easy credit card scanning in mobile apps.

Stay up to date
---------------

Please be sure to keep your app up to date with the latest version of the SDK.
All releases follow [semantic versioning](http://semver.org/).

You can receive updates about new versions via a few different channels:

* Follow [@cardio](https://twitter.com/cardio) (also great to send us feedback)
* Subscribe to our [card-io-sdk-announce](https://groups.google.com/forum/#!forum/card-io-sdk-announce) list.
* "Watch" this GitHub repository

Also be sure to check and post to the [Stack Overflow card.io tag](http://stackoverflow.com/questions/tagged/card.io).

Instructions
------------

The card.io Android SDK is a single card.io.jar file and two native libraries.

The information in this guide is enough to get started. For additional details, see our **[javadoc](http://card-io.github.io/card.io-Android-SDK/)**.

### Sign up for card.io

*   To use the card.io SDK, you'll need to [sign up](https://www.card.io/accounts/register/developer) and [get an app token](https://www.card.io/apps/). 

### Requirements for card scanning

*   Rear-facing camera.
*   Android SDK version 8 (Android 2.2) or later.
*   ARMv7 processor.

A manual entry fallback mode is provided for devices that do not meet these requirements.

### Instructions

1. Get the latest SDK by cloning this repo or [downloading an archive of the most recent tag](https://github.com/card-io/card.io-Android-SDK/tags).

2. Extract the card.io SDK

    1. Unzip the tag archive into your project directory or copy `libs/*` into your projects `libs/` directory. 
       - Gradle users with android-gradle-plugin version `0.7.2+` should put `card.io.jar` in `libs/` and the subfolders containing `*.so` files into `src/main/jniLibs`
       - Note that the subdirectories containing `.so` files are important and should not be changed.

    2. If using Eclipse, right-click <libs>libs/card.io.jar</libs> then select "Build Path" &rarr; "Add to Build Path".

3. Edit AndroidManifest.xml. We're going to add a few additional items in here:

    1. Ensure your minimum SDK level is 8 or above. You should have an element like this in `<manifest>`:


        ```xml
        <uses-sdk android:minSdkVersion="8" />
        ```

    2. Also in your `<manifest>` element, make sure the following permissions and features are present:

        ```xml
        <!-- Permission to access network state - required -->
        <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

                <!-- Permission to access internet - required -->
        <uses-permission android:name="android.permission.INTERNET" />

                <!-- Permission to use camera - required -->
        <uses-permission android:name="android.permission.CAMERA" />

                <!-- Permission to vibrate - recommended, allows vibration feedback on scan -->
        <uses-permission android:name="android.permission.VIBRATE" />

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

4. Before you build in release mode, make sure to adjust your proguard configuration by adding the following to `proguard.cnf`:

    ```
    -keep class io.card.**
    -keepclassmembers class io.card.** {
        *;
    }
    ```

### Sample code

First, we'll assume that you're going to launch the scanner from a button, and that you've set the button's onClick handler in the layout XML via `android:onClick="onScanPress"`. Then, add the method as:

```java
public void onScanPress(View v) {
    Intent scanIntent = new Intent(this, CardIOActivity.class);

    // required for authentication with card.io
    scanIntent.putExtra(CardIOActivity.EXTRA_APP_TOKEN, MY_CARDIO_APP_TOKEN);

    // customize these values to suit your needs.
    scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true); // default: true
    scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false); // default: false
    scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false); // default: false

    // MY_SCAN_REQUEST_CODE is arbitrary and is only used within this activity.
    startActivityForResult(scanIntent, MY_SCAN_REQUEST_CODE);
}
```

Next, we'll override `onActivityResult` to get the scan result.

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
* The card.io zip bundle is designed to be unzipped in to the your project directory.
    * **You should not attempt to change this directory structure.**
    * In particular, Android looks for files matching `libs/card.io.jar` and `libs/{processor}/*.so`.
* If upgrading the card.io SDK, first remove all card.io libraries so that you don't accidentally ship obsolete or unnecessary libraries. The bundled libraries may change.
* Processing images can be memory intensive.
    * [Memory Analysis for Android Applications](http://android-developers.blogspot.com/2011/03/memory-analysis-for-android.html) provides some useful information about how to track and reduce your app's memory useage.
* card.io uses [SSL pinning](http://blog.thoughtcrime.org/authenticity-is-broken-in-ssl-but-your-app-ha) to protect against man-in-the-middle attacks. We encourage you to do the same.
