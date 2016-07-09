package com.sakuna63.tumblrin.application.di.component;

import android.content.Context;

import com.sakuna63.tumblrin.application.contract.presenter.login.OauthHelper;
import com.sakuna63.tumblrin.application.di.module.ActivityModule;
import com.sakuna63.tumblrin.application.di.module.ApplicationModule;
import com.sakuna63.tumblrin.application.misc.AccountManager;
import com.tumblr.jumblr.JumblrClient;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    JumblrClient jumblrClient();
    AccountManager accountManger();
    OauthHelper oauthHelper();
}
