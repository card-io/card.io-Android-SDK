package io.card.payment;

/* CardScannerTester.java
 * See the file "LICENSE.md" for the full license governing this code.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.view.SurfaceHolder;

import java.io.IOException;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

public class CardScannerTester extends CardScanner {

    private static final long FRAME_INTERVAL = (long) (1000.0 / 30);

    private static String sCardAssetName;

    private boolean mScanAllowed;
    private Handler mHandler;
    private byte[] mFrame;

    public static void setCardAsset(String cardAssetName) {
        sCardAssetName = cardAssetName;
    }

    public CardScannerTester(CardIOActivity scanActivity, int currentFrameOrientation) {
        super(scanActivity, currentFrameOrientation);
        useCamera = false;
        mScanAllowed = false;
        mHandler = new Handler();

        try {
            Bitmap bitmap = BitmapFactory.decodeStream(getInstrumentation().getContext().getAssets()
                    .open("test_card_images/" + sCardAssetName));
            mFrame = getNV21FormattedImage(bitmap.getWidth(), bitmap.getHeight(), bitmap);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Runnable mFrameRunnable = new Runnable() {
        @Override
        public void run() {
            if (!mScanAllowed) {
                return;
            }

            onPreviewFrame(mFrame, null);
            mHandler.postDelayed(this, FRAME_INTERVAL);
        }
    };

    @Override
    boolean resumeScanning(SurfaceHolder holder) {
        boolean result = super.resumeScanning(holder);
        mScanAllowed = true;
        mHandler.postDelayed(mFrameRunnable, FRAME_INTERVAL);
        return result;
    }

    @Override
    public void pauseScanning() {
        mScanAllowed = false;
        super.pauseScanning();
    }

    private byte[] getNV21FormattedImage(int width, int height, Bitmap bitmap) {
        int [] argb = new int[width * height];
        byte [] yuv = new byte[width * height * 3 / 2];

        bitmap.getPixels(argb, 0, width, 0, 0, width, height);
        encodeYUV420SP(yuv, argb, width, height);
        bitmap.recycle();

        return yuv;
    }

    private void encodeYUV420SP(byte[] yuv420sp, int[] argb, int width, int height) {
        int frameSize = width * height;
        int yIndex = 0;
        int uvIndex = frameSize;

        int R, G, B, Y, U, V;
        int index = 0;
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                R = (argb[index] & 0xff0000) >> 16;
                G = (argb[index] & 0xff00) >> 8;
                B = (argb[index] & 0xff);

                // well known RGB to YUV algorithm
                Y = ( (  66 * R + 129 * G +  25 * B + 128) >> 8) +  16;
                U = ( ( -38 * R -  74 * G + 112 * B + 128) >> 8) + 128;
                V = ( ( 112 * R -  94 * G -  18 * B + 128) >> 8) + 128;

                // NV21 has a plane of Y and interleaved planes of VU each sampled by a factor of 2
                // meaning for every 4 Y pixels there are 1 V and 1 U.  Note the sampling is every
                // other pixel AND every other scanline.
                yuv420sp[yIndex++] = (byte) ((Y < 0) ? 0 : ((Y > 255) ? 255 : Y));
                if (j % 2 == 0 && index % 2 == 0) {
                    yuv420sp[uvIndex++] = (byte)((V<0) ? 0 : ((V > 255) ? 255 : V));
                    yuv420sp[uvIndex++] = (byte)((U<0) ? 0 : ((U > 255) ? 255 : U));
                }

                index ++;
            }
        }
    }
}
