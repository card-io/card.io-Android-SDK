package io.card.development;

/* SampleActivity.java
 * See the file "LICENSE.md" for the full license governing this code.
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.card.payment.CardIOActivity;
import io.card.payment.CardType;
import io.card.payment.CreditCard;
import io.card.payment.i18n.StringKey;
import io.card.payment.i18n.SupportedLocale;
import io.card.payment.i18n.locales.LocalizedStringsList;

public class SampleActivity extends Activity {

    protected static final String TAG = SampleActivity.class.getSimpleName();

    private static final int REQUEST_SCAN = 100;
    private static final int REQUEST_AUTOTEST = 200;

    private CheckBox mManualToggle;
    private CheckBox mEnableExpiryToggle;
    private CheckBox mScanExpiryToggle;
    private CheckBox mCvvToggle;
    private CheckBox mPostalCodeToggle;
    private CheckBox mPostalCodeNumericOnlyToggle;
    private CheckBox mCardholderNameToggle;
    private CheckBox mSuppressManualToggle;
    private CheckBox mSuppressConfirmationToggle;
    private CheckBox mSuppressScanToggle;

    private TextView mResultLabel;
    private ImageView mResultImage;
    private ImageView mResultCardTypeImage;

    private boolean autotestMode;
    private int numAutotestsPassed;
    private CheckBox mUseCardIOLogoToggle;
    private CheckBox mShowPayPalActionBarIconToggle;
    private CheckBox mKeepApplicationThemeToggle;
    private Spinner mLanguageSpinner;
    private EditText mUnblurEdit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_activity);

        mManualToggle = (CheckBox) findViewById(R.id.force_manual);
        mEnableExpiryToggle = (CheckBox) findViewById(R.id.gather_expiry);
        mScanExpiryToggle = (CheckBox) findViewById(R.id.scan_expiry);
        mCvvToggle = (CheckBox) findViewById(R.id.gather_cvv);
        mPostalCodeToggle = (CheckBox) findViewById(R.id.gather_postal_code);
        mPostalCodeNumericOnlyToggle = (CheckBox) findViewById(R.id.postal_code_numeric_only);
        mCardholderNameToggle = (CheckBox) findViewById(R.id.gather_cardholder_name);
        mSuppressManualToggle = (CheckBox) findViewById(R.id.suppress_manual);
        mSuppressConfirmationToggle = (CheckBox) findViewById(R.id.suppress_confirmation);
        mSuppressScanToggle = (CheckBox) findViewById(R.id.detect_only);

        mUseCardIOLogoToggle = (CheckBox) findViewById(R.id.use_card_io_logo);
        mShowPayPalActionBarIconToggle = (CheckBox) findViewById(R.id.show_paypal_action_bar_icon);
        mKeepApplicationThemeToggle = (CheckBox) findViewById(R.id.keep_application_theme);

        mLanguageSpinner = (Spinner) findViewById(R.id.language);
        mUnblurEdit = (EditText) findViewById(R.id.unblur);

        mResultLabel = (TextView) findViewById(R.id.result);
        mResultImage = (ImageView) findViewById(R.id.result_image);
        mResultCardTypeImage = (ImageView) findViewById(R.id.result_card_type_image);

        TextView version = (TextView) findViewById(R.id.version);
        version.setText("card.io library: " + CardIOActivity.sdkVersion() + "\n" +
                "Build date: " + CardIOActivity.sdkBuildDate());

        setScanExpiryEnabled();
        setupLanguageList();
    }

    private void setScanExpiryEnabled() {
        mScanExpiryToggle.setEnabled(mEnableExpiryToggle.isChecked());
    }

    public void onExpiryToggle(View v) {
        setScanExpiryEnabled();
    }

    public void onScan(View pressed) {
        Intent intent = new Intent(this, CardIOActivity.class)
                .putExtra(CardIOActivity.EXTRA_NO_CAMERA, mManualToggle.isChecked())
                .putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, mEnableExpiryToggle.isChecked())
                .putExtra(CardIOActivity.EXTRA_SCAN_EXPIRY, mScanExpiryToggle.isChecked())
                .putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, mCvvToggle.isChecked())
                .putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, mPostalCodeToggle.isChecked())
                .putExtra(CardIOActivity.EXTRA_RESTRICT_POSTAL_CODE_TO_NUMERIC_ONLY, mPostalCodeNumericOnlyToggle.isChecked())
                .putExtra(CardIOActivity.EXTRA_REQUIRE_CARDHOLDER_NAME, mCardholderNameToggle.isChecked())
                .putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, mSuppressManualToggle.isChecked())
                .putExtra(CardIOActivity.EXTRA_USE_CARDIO_LOGO, mUseCardIOLogoToggle.isChecked())
                .putExtra(CardIOActivity.EXTRA_LANGUAGE_OR_LOCALE, (String) mLanguageSpinner.getSelectedItem())
                .putExtra(CardIOActivity.EXTRA_USE_PAYPAL_ACTIONBAR_ICON, mShowPayPalActionBarIconToggle.isChecked())
                .putExtra(CardIOActivity.EXTRA_KEEP_APPLICATION_THEME, mKeepApplicationThemeToggle.isChecked())
                .putExtra(CardIOActivity.EXTRA_GUIDE_COLOR, Color.GREEN)
                .putExtra(CardIOActivity.EXTRA_SUPPRESS_CONFIRMATION, mSuppressConfirmationToggle.isChecked())
                .putExtra(CardIOActivity.EXTRA_SUPPRESS_SCAN, mSuppressScanToggle.isChecked())
                .putExtra(CardIOActivity.EXTRA_RETURN_CARD_IMAGE, true);

        try {
            int unblurDigits = Integer.parseInt(mUnblurEdit.getText().toString());
            intent.putExtra(CardIOActivity.EXTRA_UNBLUR_DIGITS, unblurDigits);
        } catch(NumberFormatException ignored) {}

        startActivityForResult(intent, REQUEST_SCAN);
    }

    public void onAutotest(View v) {
        Log.i(TAG, "\n\n\n ============================== \n" + "successfully completed "
                + numAutotestsPassed + " tests\n" + "beginning new test run\n");

        Intent intent = new Intent(this, CardIOActivity.class)
                .putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, false)
                .putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false)
                .putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false)
                .putExtra(CardIOActivity.EXTRA_REQUIRE_CARDHOLDER_NAME, false)
                .putExtra("debug_autoAcceptResult", true);

        startActivityForResult(intent, REQUEST_AUTOTEST);

        autotestMode = true;
    }

    @Override
    public void onStop() {
        super.onStop();

        mResultLabel.setText("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.v(TAG, "onActivityResult(" + requestCode + ", " + resultCode + ", " + data + ")");

        String outStr = new String();
        Bitmap cardTypeImage = null;

        if ((requestCode == REQUEST_SCAN || requestCode == REQUEST_AUTOTEST) && data != null
                && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
            CreditCard result = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);
            if (result != null) {
                outStr += "Card number: " + result.getRedactedCardNumber() + "\n";

                CardType cardType = result.getCardType();
                cardTypeImage = cardType.imageBitmap(this);
                outStr += "Card type: " + cardType.name() + " cardType.getDisplayName(null)="
                        + cardType.getDisplayName(null) + "\n";

                if (mEnableExpiryToggle.isChecked()) {
                    outStr += "Expiry: " + result.expiryMonth + "/" + result.expiryYear + "\n";
                }

                if (mCvvToggle.isChecked()) {
                    outStr += "CVV: " + result.cvv + "\n";
                }

                if (mPostalCodeToggle.isChecked()) {
                    outStr += "Postal Code: " + result.postalCode + "\n";
                }

                if (mCardholderNameToggle.isChecked()) {
                    outStr += "Cardholder Name: " + result.cardholderName + "\n";
                }
            }

            if (autotestMode) {
                numAutotestsPassed++;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onAutotest(null);
                    }
                }, 500);
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            autotestMode = false;
        }

        Bitmap card = CardIOActivity.getCapturedCardImage(data);
        mResultImage.setImageBitmap(card);
        mResultCardTypeImage.setImageBitmap(cardTypeImage);

        Log.i(TAG, "Set result: " + outStr);

        mResultLabel.setText(outStr);
    }

    private void setupLanguageList() {
        List<String> languages = new ArrayList<>();
        for (SupportedLocale<StringKey> locale : LocalizedStringsList.ALL_LOCALES) {
            languages.add(locale.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, languages);
        mLanguageSpinner.setAdapter(adapter);
        mLanguageSpinner.setSelection(adapter.getPosition("en"));
    }
}
