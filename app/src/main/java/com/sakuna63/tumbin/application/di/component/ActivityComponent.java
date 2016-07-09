package com.sakuna63.tumbin.application.di.component;

import com.sakuna63.tumbin.application.activity.PostActivity;
import com.sakuna63.tumbin.application.di.module.ActivityModule;
import com.sakuna63.tumbin.application.di.scope.ActivityScope;
import com.sakuna63.tumbin.data.api.TumblrService;

import dagger.Component;
import io.realm.RealmConfiguration;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(PostActivity activity);

    RealmConfiguration realmConfiguration();

    TumblrService tumblrService();
}
