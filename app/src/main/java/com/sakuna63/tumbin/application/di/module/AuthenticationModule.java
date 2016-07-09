package com.sakuna63.tumbin.application.di.module;

import android.content.Context;

import com.sakuna63.tumbin.BuildConfig;
import com.sakuna63.tumbin.application.misc.AccountManager;
import com.sakuna63.tumbin.data.model.Token;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthProvider;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;

@Module
public class AuthenticationModule {
    private static final String AUTHORIZE_URL = "https://www.tumblr.com/oauth/authorize?oauth_token=%s";
    private static final String REQUEST_TOKEN_RESOURCE = "http://www.tumblr.com/oauth/request_token";
    private static final String ACCESS_TOKEN_RESOURCE = "http://www.tumblr.com/oauth/access_token";

    @Provides
    @Singleton
    OAuthProvider oAuthProvider() {
        return new DefaultOAuthProvider(
                REQUEST_TOKEN_RESOURCE,
                ACCESS_TOKEN_RESOURCE,
                AUTHORIZE_URL
        );
    }

    @Provides
    @Singleton
    OAuthConsumer oAuthConsumer(AccountManager accountManager) {
        OkHttpOAuthConsumer consumer =
                new OkHttpOAuthConsumer(BuildConfig.CONSUMER_KEY, BuildConfig.CONSUMER_KEY_SECRET);
        if (accountManager.isLoggedIn()) {
            Token token = accountManager.getToken();
            //noinspection ConstantConditions
            consumer.setTokenWithSecret(token.getToken(), token.getTokenSecret());
        }
        return consumer;
    }

    @Singleton
    @Provides
    AccountManager accountManager(Context context) {
        return new AccountManager(context);
    }
}
