package com.sakuna63.tumblrin.application.di.component;

import com.sakuna63.tumblrin.application.contract.presenter.login.OauthHelper;
import com.sakuna63.tumblrin.application.di.module.ApiModule;
import com.sakuna63.tumblrin.application.di.module.ApplicationModule;
import com.sakuna63.tumblrin.application.di.module.AuthenticationModule;
import com.sakuna63.tumblrin.application.misc.AccountManager;
import com.sakuna63.tumblrin.data.net.TumblrService;

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
