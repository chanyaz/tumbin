package com.sakuna63.tumblrin.application.di.module;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;

import com.sakuna63.tumblrin.application.di.scope.ActivityScope;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private final RxAppCompatActivity activity;

    public ActivityModule(RxAppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    Activity activity() {
        return activity;
    }

    @Provides
    @ActivityScope
    Context context() {
        return activity;
    }

    @Provides
    @ActivityScope
    LayoutInflater layoutInflater() {
        return activity.getLayoutInflater();
    }

    @Provides
    @ActivityScope
    LifecycleTransformer lifecycleTransformer() {
        return activity.bindToLifecycle();
    }
}
