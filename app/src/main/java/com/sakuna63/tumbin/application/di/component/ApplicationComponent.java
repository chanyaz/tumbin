package com.sakuna63.tumbin.application.di.component;

import com.sakuna63.tumbin.application.di.module.ApiModule;
import com.sakuna63.tumbin.application.di.module.ApplicationModule;
import com.sakuna63.tumbin.application.di.module.AuthenticationModule;
import com.sakuna63.tumbin.application.misc.AccountManager;
import com.sakuna63.tumbin.data.api.TumblrService;
import com.sakuna63.tumbin.application.contract.presenter.login.OauthHelper;

import javax.inject.Singleton;

import dagger.Component;
import io.realm.RealmConfiguration;

@Singleton
@Component(modules = {ApplicationModule.class, AuthenticationModule.class, ApiModule.class})
public interface ApplicationComponent {
    AccountManager accountManger();

    OauthHelper oauthHelper();

    RealmConfiguration realmConfiguration();

    TumblrService tumblrService();
}
