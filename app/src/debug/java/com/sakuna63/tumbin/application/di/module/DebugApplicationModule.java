package com.sakuna63.tumbin.application.di.module;

import android.app.Application;

import io.realm.RealmConfiguration;

public class DebugApplicationModule extends ApplicationModule {

    public DebugApplicationModule(Application app) {
        super(app);
    }

    @Override
    RealmConfiguration realmConfiguration() {
        return new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
    }
}
