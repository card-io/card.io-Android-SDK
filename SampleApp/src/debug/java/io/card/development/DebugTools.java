package io.card.development;

import android.app.Application;
import android.os.StrictMode;

import com.squareup.leakcanary.LeakCanary;

public class DebugTools {

    public static void setup(Application application) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .penaltyDeath()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectFileUriExposure()
                .detectLeakedClosableObjects()
                .detectLeakedRegistrationObjects()
                .detectLeakedSqlLiteObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());

        LeakCanary.install(application);
    }
}
