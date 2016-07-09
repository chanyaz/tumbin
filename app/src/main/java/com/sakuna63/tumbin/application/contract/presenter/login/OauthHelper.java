package com.sakuna63.tumbin.application.contract.presenter.login;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sakuna63.tumbin.BuildConfig;
import com.sakuna63.tumbin.data.model.Token;

import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import rx.Single;

@Singleton
public class OauthHelper {

    @NonNull
    private final OAuthConsumer oAuthConsumer;

    @NonNull
    private final OAuthProvider oAuthProvider;

    @Inject
    public OauthHelper(@NonNull OAuthConsumer oAuthConsumer, @NonNull OAuthProvider oauthProvider) {
        this.oAuthConsumer = oAuthConsumer;
        this.oAuthProvider = oauthProvider;
    }

    @Nullable
    public static String extractVerifier(@NonNull String dataString) {
        Uri uri = Uri.parse(dataString);
        return uri.getQueryParameter("oauth_verifier");
    }

    @NonNull
    public Single<String> getAuthorizationUrl() {
        return Single.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return oAuthProvider
                        .retrieveRequestToken(oAuthConsumer, BuildConfig.CALLBACK_URL);
            }
        });
    }

    @NonNull
    public Single<Token> getAccessToken(@NonNull final String verifierStr) {
        return Single.fromCallable(new Callable<Token>() {
            @Override
            public Token call() throws Exception {
                oAuthProvider.retrieveAccessToken(oAuthConsumer, verifierStr);
                return new Token(oAuthConsumer.getToken(), oAuthConsumer.getTokenSecret());
            }
        });
    }
}
