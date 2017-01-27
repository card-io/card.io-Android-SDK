package io.card.test;


import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;

import static android.support.test.InstrumentationRegistry.getTargetContext;

public class CustomActivityTestRule<T extends Activity> extends android.support.test.rule.ActivityTestRule<T> {

    private KeyguardManager.KeyguardLock mKeyguardLock;

    public CustomActivityTestRule(Class<T> activityClass) {
        super(activityClass);
        init();
    }

    public CustomActivityTestRule(Class<T> activityClass, boolean initialTouchMode) {
        super(activityClass, initialTouchMode);
        init();
    }

    public CustomActivityTestRule(Class<T> activityClass, boolean initialTouchMode, boolean launchActivity) {
        super(activityClass, initialTouchMode, launchActivity);
        init();
    }

    private void init() {
        mKeyguardLock = ((KeyguardManager) getTargetContext().getSystemService(Context.KEYGUARD_SERVICE))
                .newKeyguardLock("CardIOTest");
        mKeyguardLock.disableKeyguard();
    }

    @Override
    protected void afterActivityFinished() {
        super.afterActivityFinished();

        mKeyguardLock.reenableKeyguard();
    }
}
