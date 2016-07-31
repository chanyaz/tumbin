package com.sakuna63.tumblrin.application.di.component;

import com.sakuna63.tumblrin.application.activity.HomeActivity;
import com.sakuna63.tumblrin.application.di.module.ActivityModule;
import com.sakuna63.tumblrin.application.di.module.PostsPresenterModule;
import com.sakuna63.tumblrin.application.di.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class,
        modules = {ActivityModule.class, PostsPresenterModule.class})
public interface HomeComponent extends ActivityComponent {
    void inject(HomeActivity activity);
}
