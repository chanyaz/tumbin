package com.sakuna63.tumbin.application.di.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.RealmConfiguration;

@Module
public class ApplicationModule {
    private final Application app;

    public ApplicationModule(Application app) {
        this.app = app;
    }

    @Provides
    Context context() {
        return app;
    }

    @Provides
    @Singleton
    RealmConfiguration realmConfiguration() {
        return new RealmConfiguration.Builder().build();
    }
}
