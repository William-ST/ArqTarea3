package com.example.arqtarea3_complete.asyntask.extra2;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by William_ST on 19/02/19.
 */

public class App extends Application implements Application.ActivityLifecycleCallbacks {

    private int activityReferences = 0;
    private boolean isActivityChangingConfigurations = false;

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (++activityReferences == 1 && !isActivityChangingConfigurations) {
            // App enters foreground
            Toast.makeText(this, "App enters foreground", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        isActivityChangingConfigurations = activity.isChangingConfigurations();
        if (--activityReferences == 0 && !isActivityChangingConfigurations) {
            // App enters background
            if (statusAppInterface != null) statusAppInterface.inBackground();
            Toast.makeText(this, "App enters background", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    private StatusAppInterface statusAppInterface;

    public interface StatusAppInterface {
        void inBackground();
    }

    public void setStatusAppInterface(StatusAppInterface statusAppInterface) {
        this.statusAppInterface = statusAppInterface;
    }
}