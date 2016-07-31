package com.sakuna63.tumblrin;

import android.app.Application;
import android.support.annotation.VisibleForTesting;

import com.sakuna63.tumblrin.application.di.component.ApplicationComponent;
import com.sakuna63.tumblrin.application.di.component.DaggerApplicationComponent;
import com.sakuna63.tumblrin.application.di.module.ApplicationModule;

import net.danlew.android.joda.JodaTimeAndroid;

public class App extends Application {

    private ApplicationComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        JodaTimeAndroid.init(this);
        initAppComponent();
    }

    private void initAppComponent() {
        appComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getAppComponent() {
        return appComponent;
    }

    @VisibleForTesting
    public void setAppComponent(ApplicationComponent appComponent) {
        this.appComponent = appComponent;
    }
}
