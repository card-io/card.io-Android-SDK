card.io Android SDK change log and release notes
================================================

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
* Distribute .aar file instead of .jar and .so files
* New extras on `CardIOActivity`
  - `EXTRA_SCAN_INSTRUCTIONS`
  - `EXTRA_HIDE_CARDIO_LOGO`
  - `EXTRA_SCAN_OVERLAY_LAYOUT_ID`
  - `EXTRA_SUPPRESS_SCAN`
  - `EXTRA_RETURN_CARD_IMAGE`
  - `EXTRA_KEEP_APPLICATION_THEME`
  - `EXTRA_USE_PAYPAL_ACTIONBAR_ICON`
* Remove deprecated extras and methods in `CardIOActivity`
  - `canReadCardWithCamera(Context)` (use `canReadCardWithCamera()` instead)
  - `EXTRA_REQUIRE_ZIP` (use `EXTRA_REQUIRE_POSTAL_CODE` instead)
* New extra `EXTRA_CAPTURED_CARD_IMAGE` returned to calling Activity 
* New class `BuildConfig`


3.2.0
-----
* Eliminate App Token. Developers no longer need to sign up on the card.io site before using card.io.
* Add Icelandic (is) to our supported localizations. (Thank you, Martin Kaplan!) 

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
* Remove any transparency from guide color
* Add language support for Arabic and Malay

3.1.3 October 9, 2013
------------------------
* Add guide color customization through `CardIOActivity.EXTRA_GUIDE_COLOR`
* Add ability to skip confirmation activity through `CardIOActivity.EXTRA_SUPPRESS_CONFIRMATION`
* Fix camera not recognized issue for some devices

3.1.2 - September 16, 2013
------------------------
* Restrict postal code maximum length
* Add convenience method to localize CardType
* Fix Hebrew phone settings detection bug
* Add SampleApp
* Reverse release_notes order

3.1.1 - August 30, 2013
------------------------
* Update globalization strings for 25 languages/locales

3.1.0 - August 13, 2013
------------------------
* Add translations of all strings into ~20 languages, in addition to American English.
* Translation choice is controlled by `CardIOActivity.EXTRA_LANGUAGE_OR_LOCALE`
* The translations that a few developers had previously created for their own apps will no longer be used by the SDK.
* NOTE: Default language, if not set by your app, will now be based upon the device's current language setting.

3.0.8 - July 3, 2013
---------------------
* Fix pre Android 4.0 hanging issue

3.0.7 - July 3, 2013
---------------------
* Fix rotation issue when starting in landscape mode
* Fix card image disappearing when rotating device in card details screen

3.0.6 - June 21, 2013
---------------------
* Add missing string to internationalization list.
* Add log note when processor is not supported.

3.0.5 - May 23, 2013
--------------------
* UI updates
* Don't require portrait for DataEntryActivity
* Fix scanner crash in landscape

3.0.4 - May 14, 2013
--------------------
* Fix orientation bugs to support some tablets.
* Use PayPal logo instead of card.io by default. (see javadoc to switch back)

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

2.4.3 - Aug 31, 2012
--------------------
* Correct layouts for high resolution cameras & xhdpi displays
* Fix crash on HTC ICS

2.4.2 - Aug 23, 2012
--------------------
* Additional null checking.
* Improve private label support.

2.4.1 - Aug 22, 2012
--------------------
* Work around a crash in some Qualcomm camera drivers. (e.g. Samsung Galaxy II S running Android 4.0.4)
* Improve efficiency of internal API.

2.4 - June 25, 2012
--------------------
* Charges are no longer supported.

2.3.3 - June 6, 2012
--------------------
* Fix a bug that caused some mod builds (e.g. CyanogenMod) to hang at the charge screen.
* Fix a problem that could have caused GPS to stay active for longer than necessary.

2.3.2 - June 4, 2012
------------------
* Support scanning in non-NEON ARMv7 devices (e.g. nVidia Tegra2)
* Support manual entry in all other devices. (Including those based on MIPS or x86)
* Fix exception in charge flow caused by users pressing the home button while the charge is processing.

*************************
IMPORTANT: You should delete libs/*/libcardio*.so from your project directory before unzipping the new SDK. (This library is obsolete and will only make your .apk bigger if it is included.)

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

2.2.1 - April 20, 2012
--------------------
* Make CardType.getLogoBitmap() a public method.
* Repackage library jar to avoid proguard parsing problems in client apps.
* Fix string display bug in the charge screens.

2.2 - April 11, 2012
--------------------

*************************
IMPORTANT: You should delete libs/armeabi*/libcardio.so from your project directory before unzipping the new SDK. (This library is obsolete and will only make your .apk bigger if it is included.)

*************************

* (2.1.2)Fix native library loading bug in Android 4.0.1-4.0.3 that could cause manual entry to be used instead of camera.
* Add capability to suppress manual number entry in scan-only mode.
* Add support for developer provided localization strings in scan-only mode.
* Handle case where device returns locations with a future timestamp that could cause charges to fail.

2.1.1 - April 9, 2012
--------------------
* Fix NullPointerException on manual entry press in scan only mode.

2.1 - April 4, 2012
--------------------
* Made the charge screens more beautiful.
* Add support for JCB cards.
* Removed first digit card detection from CardType. Use CardType.fromCardNumber(String) instead.
* Remove deprecated CardIOCreditCardResult. Use CreditCard instead.
* Gather additional information for fraud detection.
* Add additional sanity checking for crash prevention.

2.0.5 - February 22, 2012
--------------------
* Performance improvements in SSL pinning
* Fix SSL pinning bug in Android 4.0

2.0.4 - February 13, 2012
--------------------
* Enhance security with SSL pinning.
* Properly check for the absence of the NEON instruction set in some ARMv7 devices. (Notably those based on nVidia Tegra 2)
* Fix bugs relating to cancel/back.

2.0.3 - January 31, 2012
--------------------
* Ensure the library passes detectAll() checks imposed by StrictMode.setThreadPolicy(...) and StrictMode.setVmPolicy(...) as of Android-14.
* Fix a crash caused by using a recycled bitmap when the user goes back after scanning a card.

2.0.2 - January 27, 2012
--------------------
* Additional error checking and catching
* Be more explicit about device problems when we fall back to manual entry
* Expose more logging to developers

2.0.1 - January 20, 2012
--------------------
* Fix crash caused by certain malformed server responses.
* Add connection status messages to logs.
* Correctly report network type. Fixes problems associated with networks other than WiFi or mobile.

2.0 - January 18, 2012
-------------------
* (new!) Support processing charges
* Rename package io.card.scan to io.card.payments
* Refactor scan interface. See https://card.io/resources/javadoc/index.html to see a complete list of deprecated methods & constants.
* Updated HTTPS library for better performance while scanning.
* Fixed crashes in Android 4.0/Ice Cream Sandwich.

1.0 - Wednesday, 8/24/2011
-------------------
* First release

