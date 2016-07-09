package com.sakuna63.tumblrin.application.di.module;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    public Activity activity() {
        return activity;
    }

    @Provides
    public Context context() {
        return activity;
    }

    @Provides
    LayoutInflater layoutInflater() {
        return activity.getLayoutInflater();
    }
}
