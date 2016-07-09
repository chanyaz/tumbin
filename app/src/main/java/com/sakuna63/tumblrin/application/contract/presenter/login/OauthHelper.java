package com.sakuna63.tumblrin.application.contract.presenter.login;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.tumblr.jumblr.exceptions.JumblrException;

import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Single;
import rx.SingleSubscriber;

@Singleton
public class OauthHelper {

    @NonNull
    private final OAuthService service;

    private Token requestToken;

    @Inject
    public OauthHelper(@NonNull OAuthService service) {
        this.service = service;
    }

    public Single<String> getAuthorizationUrl() {
        return Single.create(new Single.OnSubscribe<String>() {
            @Override
            public void call(SingleSubscriber<? super String> subscriber) {
                try {
                    requestToken = service.getRequestToken();
                    String url = service.getAuthorizationUrl(requestToken);
                    subscriber.onSuccess(url);
                } catch (JumblrException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public Single<Token> getAccessToken(final String verifierStr) {
        return Single.create(new Single.OnSubscribe<Token>() {
            @Override
            public void call(SingleSubscriber<? super Token> subscriber) {
                try {
                    Verifier verifier = new Verifier(verifierStr);
                    Token accessToken = service.getAccessToken(requestToken, verifier);
                    subscriber.onSuccess(accessToken);
                } catch (JumblrException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public String extractVerifier(String dataString) {
        Uri uri = Uri.parse(dataString);
        return uri.getQueryParameter("oauth_verifier");
    }
}
