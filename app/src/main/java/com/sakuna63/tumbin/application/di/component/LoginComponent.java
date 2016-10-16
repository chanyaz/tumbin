package com.sakuna63.tumbin.application.di.component;

import com.sakuna63.tumbin.application.activity.LoginActivity;
import com.sakuna63.tumbin.application.di.module.ActivityModule;
import com.sakuna63.tumbin.application.di.module.LoginPresenterModule;
import com.sakuna63.tumbin.application.di.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class,
        modules = {ActivityModule.class, LoginPresenterModule.class})
public interface LoginComponent extends ActivityComponent {
    void inject(LoginActivity activity);
}