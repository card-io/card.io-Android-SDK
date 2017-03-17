card.io Android SDK change log and release notes
================================================

5.5.1
-----
* Remove sample app signing (fixes [#125](https://github.com/card-io/card.io-Android-source/issues/125)).
* Fix manual entry Activity started twice when rejecting camera permission (fixes [#128](https://github.com/card-io/card.io-Android-source/pull/128), [#150](https://github.com/card-io/card.io-Android-SDK/issues/150)).
* Remove unnecessary logging (fixes [#184](https://github.com/card-io/card.io-Android-SDK/issues/184)).
* Compile distributed package with NDK r14.

5.5.0
-----
* Update Gradle build plugin to 2.2.3.
* Update compile and target SDK versions to 25.
* Update NDK to r13b.
* Increase `minSdkVersion` to 16.
* Upgrade OpenCV to 2.4.13.

5.4.2
-----
* Add Mastercard 2-series support.
* Bump compile SDK to 24 for reals.
* Compile distributed package with NDK to r12b (previous was r11c).
* Update Android Gradle plugin to 2.2.0.

5.4.1
-----
* Add ability to specify an alternative search path for the native libraries via `CardIONativeLibsConfig` [card.io-Android-source#86](https://github.com/card-io/card.io-Android-source/pull/86). Thank you Thorben Primke!
* Updated gradle plugin and wrapper versions.
* Bump compile SDK to 24.

5.4.0
-----
* Add ability to blur all digits in the scanned card image, minus any number of digits to remain unblurred, enabled via `CardIOActivity.EXTRA_UNBLUR_DIGITS`. Thank you Michael Schmoock!
* Fix issue where Maestro cards were not correctly recognized [#154](https://github.com/card-io/card.io-Android-SDK/issues/154).
* Fix issue on Android 23 and above where `CardIOActivity#canReadCardWithCamera()` would return the incorrect value if permissions had not been granted [#136](https://github.com/card-io/card.io-Android-SDK/issues/136).  Now defaults to `true` in such cases.
* Add missing locales to javadocs [card.io-Android-source#75](https://github.com/card-io/card.io-Android-source/issues/75).
* Upgrade gradle to 2.13.
* Upgrade Android Gradle plugin to 2.1.0.

5.3.4
-----
* Fix crash on Android 23 and above where `onRequestPermissionsResult()` returns an empty array [card.io-Android-source#70](https://github.com/card-io/card.io-Android-source/issues/70).

5.3.3
-----
* Fix newline issue in ES locale [#142](https://github.com/card-io/card.io-Android-SDK/issues/142).
* Fix build issue with ndk 11 [card.io-Android-source#60](https://github.com/card-io/card.io-Android-source/issues/60).
* Upgrade gradle to 2.12.
* Upgrade Android Gradle plugin to 2.0.0.

5.3.2
-----
* Fix issue where Android 23 and above devices would crash when the library's `.so` files were removed [PayPal-Android-SDK#279](https://github.com/paypal/PayPal-Android-SDK/issues/279).

5.3.1
-----
* Fix issue where the camera was flipped when the app was backgrounded with card.io open [#118](https://github.com/card-io/card.io-Android-SDK/issues/118).
* Add proguard config to `aar` file [#112](https://github.com/card-io/card.io-Android-SDK/issues/112), [#117](https://github.com/card-io/card.io-Android-SDK/issues/117).

5.3.0
-----
* Add option for only numeric input for postal code, `EXTRA_RESTRICT_POSTAL_CODE_TO_NUMERIC_ONLY` [#100](https://github.com/card-io/card.io-Android-SDK/issues/100).

5.2.0
-----
* Add Cardholder Name to list of available manual entry fields, enabled via `CardIOActivity.EXTRA_REQUIRE_CARDHOLDER_NAME`. Thank you Dan Nizri and Zach Sweigart!
* Fix issue where certain devices would show the camera preview upside down [#91](https://github.com/card-io/card.io-Android-SDK/issues/91).
* Fix issue where null could be set in the return bundle value for `CardIOActivity.EXTRA_SCAN_RESULT`.
* Upgrade build tools.

5.1.2
-----
* Fix bug where denying the camera permission on Android 23 results in invalid data [#37-source](https://github.com/card-io/card.io-Android-source/issues/37).

5.1.1
-----
* Fix bug where ActionBar was not properly hiding immediately after accepting the Camera permission.

5.1.0
-----
* Add arm64-v8a processor support [#33-source](https://github.com/card-io/card.io-Android-source/issues/33), [#51](https://github.com/card-io/card.io-Android-SDK/issues/51).
* Add x86 processor support [#26-source](https://github.com/card-io/card.io-Android-source/issues/26).
* Add x86_64 processor support.
* Add support for Android 23 new permission model for the Camera permission [#78](https://github.com/card-io/card.io-Android-SDK/issues/78).  When permission is granted, the SDK performs as in previous versions.  When permission is or has already been denied, the SDK falls back to manual entry.  Note: this SDK does not call the `shouldShowRequestPermissionRationale()` method and does not show a rationale.  It is up to the implementor whether or not to show the Camera permission rationale before opening the SDK.
* Populate CardIOActivity.EXTRA_CAPTURED_CARD_IMAGE when confirmation is shown [#10-source](https://github.com/card-io/card.io-Android-source/issues/10).
* Fix issue where setting `EXTRA_KEEP_APPLICATION_THEME` would not create buttons that matched the theme [#24-source](https://github.com/card-io/card.io-Android-source/issues/24).
* Add a default edit text hint color [#22-source](https://github.com/card-io/card.io-Android-source/issues/22).
* Fix leaking IntentReceiver [#76](https://github.com/card-io/card.io-Android-SDK/issues/76).

5.0.1
-----
* Prevent screenshots when the app is backgrounded via [FLAG_SECURE](http://developer.android.com/reference/android/view/WindowManager.LayoutParams.html#FLAG_SECURE).
* Fix issue where arm64-v8a devices were not allowing the scanning of devices [#62](https://github.com/card-io/card.io-Android-SDK/issues/62)).

5.0.0
-----
* Add automatic expiry-scanning.
You can disable this feature via the new `EXTRA_SCAN_EXPIRY` extra of `CardIOActivity`.
Note: Expiry scans will not infrequently fail to obtain the correct expiry date.
We are continuing to work to improve expiry-scanning accuracy.
* Fix crash when the `DataEntryActivity` is missing extras [#19](https://github.com/card-io/card.io-Android-SDK/issues/19)).

4.0.2
-----
* Fix crash caused when an application's theme specifies no action bar [#44](https://github.com/card-io/card.io-Android-SDK/issues/44)).
* Use the application theme to define most of the UI's colors.

4.0.1
-----
* Minor bug fixes.

4.0.0
-----
* Distribute .aar file instead of .jar and .so files.
* New extras on `CardIOActivity`: `EXTRA_SCAN_INSTRUCTIONS`, `EXTRA_HIDE_CARDIO_LOGO`, `EXTRA_SCAN_OVERLAY_LAYOUT_ID`, `EXTRA_SUPPRESS_SCAN`, `EXTRA_RETURN_CARD_IMAGE`, `EXTRA_KEEP_APPLICATION_THEME`, and `EXTRA_USE_PAYPAL_ACTIONBAR_ICON`.
* Remove deprecated extras and methods in `CardIOActivity`: `canReadCardWithCamera(Context)` and `EXTRA_REQUIRE_ZIP`.
* New extra `EXTRA_CAPTURED_CARD_IMAGE` returned to calling Activity.
* New class `BuildConfig`.


3.2.0
-----
* Eliminate App Token. Developers no longer need to sign up on the card.io site before using card.io.
* Add Icelandic (is) to our supported localizations. Thank you, Martin Kaplan!

3.1.6
-----
* Add support for Diners Club and China UnionPay cards.
* Rename zh-Hant_HK -> zh-Hant so that HK is chosen by default for other regions.

3.1.5 May 1, 2014
-----------------
* Add Thai (th) to our supported localizations.
* Update PayPal logo.

3.1.4 January 3, 2014
------------------------
* Remove any transparency from guide color.
* Add language support for Arabic and Malay.

3.1.3 October 9, 2013
------------------------
* Add guide color customization through `CardIOActivity.EXTRA_GUIDE_COLOR`.
* Add ability to skip confirmation activity through `CardIOActivity.EXTRA_SUPPRESS_CONFIRMATION`.
* Fix camera not recognized issue for some devices.

3.1.2 - September 16, 2013
------------------------
* Restrict postal code maximum length.
* Add convenience method to localize CardType.
* Fix Hebrew phone settings detection bug.
* Add SampleApp.
* Reverse release_notes order.

3.1.1 - August 30, 2013
------------------------
* Update globalization strings for 25 languages/locales.

3.1.0 - August 13, 2013
------------------------
* Add translations of all strings into ~20 languages, in addition to American English.
* Translation choice is controlled by `CardIOActivity.EXTRA_LANGUAGE_OR_LOCALE`.
* The translations that a few developers had previously created for their own apps will no longer be used by the SDK.
* NOTE: Default language, if not set by your app, will now be based upon the device's current language setting.

3.0.8 - July 3, 2013
---------------------
* Fix pre Android 4.0 hanging issue.

3.0.7 - July 3, 2013
---------------------
* Fix rotation issue when starting in landscape mode.
* Fix card image disappearing when rotating device in card details screen.

3.0.6 - June 21, 2013
---------------------
* Add missing string to internationalization list.
* Add log note when processor is not supported.

3.0.5 - May 23, 2013
--------------------
* UI updates.
* Don't require portrait for DataEntryActivity.
* Fix scanner crash in landscape.

3.0.4 - May 14, 2013
--------------------
* Fix orientation bugs to support some tablets.
* Use PayPal logo instead of card.io by default. See javadoc to switch back.

3.0.3 - Nov 27, 2012
--------------------
* Fix a couple of NPEs associated with the back button.

3.0.2 - Oct 26, 2012
--------------------
* Fix NPE if values/strings.xml is missing from the project.
* Fix native crash if a second scanner is launched from `onActivityResult()` of completion of the first.

3.0.1 - Oct 8, 2012
--------------------
* Client side scanning!

3.0.0
--------------------
* Skipped.

2.4.3 - Aug 31, 2012
--------------------
* Correct layouts for high resolution cameras & xhdpi displays.
* Fix crash on HTC ICS.

2.4.2 - Aug 23, 2012
--------------------
* Additional null checking.
* Improve private label support.

2.4.1 - Aug 22, 2012
--------------------
* Work around a crash in some Qualcomm camera drivers, e.g. Samsung Galaxy II S running Android 4.0.4.
* Improve efficiency of internal API.

2.4.0 - June 25, 2012
--------------------
* Charges are no longer supported.

2.3.3 - June 6, 2012
--------------------
* Fix a bug that caused some mod builds (e.g. CyanogenMod) to hang at the charge screen.
* Fix a problem that could have caused GPS to stay active for longer than necessary.

2.3.2 - June 4, 2012
------------------
* Support scanning in non-NEON ARMv7 devices, e.g. nVidia Tegra2.
* Support manual entry in all other devices. Including those based on MIPS or x86.
* Fix exception in charge flow caused by users pressing the home button while the charge is processing.

*************************
IMPORTANT: You should delete libs/*/libcardio*.so from your project directory before unzipping the new SDK. This library is obsolete and will only make your .apk bigger if it is included.

The bundled libraries are:

	libs/card.io.jar
	libs/armeabi/libcardioDecider.so
	libs/armeabi-v7a/libcardioDecider.so
	libs/armeabi-v7a/libcardioRecognizer.so
	libs/armeabi-v7a/libcardioRecognizer_tegra2.so
	libs/armeabi-v7a/libopencv_core.so
	libs/armeabi-v7a/libopencv_imgproc.so
	libs/mips/libcardioDecider.so
	libs/x86/libcardioDecider.so

Note that if your app is not targeting x86 or MIPS, you can safely leave out these libraries. However, doing so will cause Google Play to filter out your app for users of those devices.

*************************

2.3.1
--------------------
* Skipped.

2.3.0
--------------------
* Skipped.

2.2.1 - April 20, 2012
--------------------
* Make CardType.getLogoBitmap() a public method.
* Repackage library jar to avoid proguard parsing problems in client apps.
* Fix string display bug in the charge screens.

2.2.0 - April 11, 2012
--------------------

*************************
IMPORTANT: You should delete libs/armeabi*/libcardio.so from your project directory before unzipping the new SDK. This library is obsolete and will only make your .apk bigger if it is included.

*************************

* (2.1.2)Fix native library loading bug in Android 4.0.1-4.0.3 that could cause manual entry to be used instead of camera.
* Add capability to suppress manual number entry in scan-only mode.
* Add support for developer provided localization strings in scan-only mode.
* Handle case where device returns locations with a future timestamp that could cause charges to fail.

2.1.1 - April 9, 2012
--------------------
* Fix NullPointerException on manual entry press in scan only mode.

2.1.0 - April 4, 2012
--------------------
* Made the charge screens more beautiful.
* Add support for JCB cards.
* Removed first digit card detection from CardType. Use CardType.fromCardNumber(String) instead.
* Remove deprecated CardIOCreditCardResult. Use CreditCard instead.
* Gather additional information for fraud detection.
* Add additional sanity checking for crash prevention.

2.0.5 - February 22, 2012
--------------------
* Performance improvements in SSL pinning.
* Fix SSL pinning bug in Android 4.0.

2.0.4 - February 13, 2012
--------------------
* Enhance security with SSL pinning.
* Properly check for the absence of the NEON instruction set in some ARMv7 devices. Notably those based on nVidia Tegra 2.
* Fix bugs relating to cancel/back.

2.0.3 - January 31, 2012
--------------------
* Ensure the library passes detectAll() checks imposed by StrictMode.setThreadPolicy(...) and StrictMode.setVmPolicy(...) as of Android-14.
* Fix a crash caused by using a recycled bitmap when the user goes back after scanning a card.

2.0.2 - January 27, 2012
--------------------
* Additional error checking and catching.
* Be more explicit about device problems when we fall back to manual entry.
* Expose more logging to developers.

2.0.1 - January 20, 2012
--------------------
* Fix crash caused by certain malformed server responses.
* Add connection status messages to logs.
* Correctly report network type. Fixes problems associated with networks other than WiFi or mobile.

2.0.0 - January 18, 2012
-------------------
* (new!) Support processing charges.
* Rename package io.card.scan to io.card.payments.
* Refactor scan interface. See https://card.io/resources/javadoc/index.html to see a complete list of deprecated methods & constants.
* Updated HTTPS library for better performance while scanning.
* Fixed crashes in Android 4.0/Ice Cream Sandwich.

1.0.0 - Wednesday, 8/24/2011
-------------------
* First release.

