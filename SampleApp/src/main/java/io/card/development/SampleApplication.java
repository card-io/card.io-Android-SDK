package io.card.development;

import android.app.Application;

public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DebugTools.setup(this);
    }
}
