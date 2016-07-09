package com.sakuna63.tumbin.application.activity;

import com.sakuna63.tumbin.App;
import com.sakuna63.tumbin.application.di.component.ActivityComponent;
import com.sakuna63.tumbin.application.di.component.ApplicationComponent;
import com.sakuna63.tumbin.application.di.module.ActivityModule;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

public abstract class BaseActivity extends RxAppCompatActivity {

    public abstract ActivityComponent getActivityComponent();

    ApplicationComponent getApplicationComponent() {
        return ((App) getApplication()).getAppComponent();
    }

    ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }
}
