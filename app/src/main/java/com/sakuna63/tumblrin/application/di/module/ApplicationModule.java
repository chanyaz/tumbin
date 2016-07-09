package com.sakuna63.tumblrin.application.di.module;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

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
}
