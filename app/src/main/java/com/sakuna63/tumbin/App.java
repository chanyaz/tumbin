package com.sakuna63.tumbin;

import android.app.Application;
import android.support.annotation.VisibleForTesting;

import com.sakuna63.tumbin.application.di.component.ApplicationComponent;
import com.sakuna63.tumbin.application.di.component.DaggerApplicationComponent;
import com.sakuna63.tumbin.application.di.module.ApplicationModule;

import net.danlew.android.joda.JodaTimeAndroid;

import io.realm.Realm;

public class App extends Application {

    private ApplicationComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        JodaTimeAndroid.init(this);
        Realm.init(this);
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
