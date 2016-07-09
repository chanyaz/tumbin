package com.sakuna63.tumblrin.application.di.component;

import com.sakuna63.tumblrin.application.activity.LoginActivity;
import com.sakuna63.tumblrin.application.di.module.ActivityModule;
import com.sakuna63.tumblrin.application.di.module.FragmentModule;
import com.sakuna63.tumblrin.application.di.module.LoginPresenterModule;
import com.sakuna63.tumblrin.application.di.scope.ActivityScope;
import com.sakuna63.tumblrin.application.di.scope.FragmentScope;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class,
        modules = {ActivityModule.class, LoginPresenterModule.class})
public interface LoginComponent extends ActivityComponent {
    void inject(LoginActivity activity);
}
