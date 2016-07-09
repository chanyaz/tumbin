package com.sakuna63.tumblrin.application.di.module;

import com.sakuna63.tumblrin.BuildConfig;
import com.sakuna63.tumblrin.application.misc.AccountManager;
import com.tumblr.jumblr.JumblrClient;

import android.app.Application;
import android.content.Context;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TumblrApi;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private final Application app;

    public ApplicationModule(Application app) {
        this.app = app;
    }

    @Provides
    Context context() {
        return app;
    }

    @Singleton
    @Provides
    JumblrClient jumblrClient(Token token) {
        JumblrClient client =
                new JumblrClient(BuildConfig.CONSUMER_KEY, BuildConfig.CONSUMER_KEY_SECRET);
        client.setToken(token);
        return client;
    }

    @Singleton
    @Provides
    Token token(AccountManager accountManager) {
        return accountManager.getToken();
    }

    @Singleton
    @Provides
    AccountManager accountManager(Context context) {
        return new AccountManager(context);
    }

    @Singleton
    @Provides
    OAuthService oAuthService() {
        return new ServiceBuilder()
                .apiKey(BuildConfig.CONSUMER_KEY)
                .apiSecret(BuildConfig.CONSUMER_KEY_SECRET)
                .callback("tumblrin://authorize")
                .provider(TumblrApi.class)
                .build();
    }
}
