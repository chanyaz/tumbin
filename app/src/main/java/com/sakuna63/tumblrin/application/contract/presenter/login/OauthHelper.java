package com.sakuna63.tumblrin.application.contract.presenter.login;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.sakuna63.tumblrin.BuildConfig;
import com.sakuna63.tumblrin.data.model.Token;

import javax.inject.Inject;
import javax.inject.Singleton;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import rx.Single;
import rx.SingleSubscriber;

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

    public Single<String> getAuthorizationUrl() {
        return Single.create(new Single.OnSubscribe<String>() {
            @SuppressWarnings("TryWithIdenticalCatches")
            @Override
            public void call(SingleSubscriber<? super String> subscriber) {
                try {
                    String url = oAuthProvider
                            .retrieveRequestToken(oAuthConsumer, BuildConfig.CALLBACK_URL);
                    subscriber.onSuccess(url);
                } catch (OAuthMessageSignerException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                } catch (OAuthNotAuthorizedException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                } catch (OAuthExpectationFailedException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                } catch (OAuthCommunicationException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        });
    }

    public Single<Token> getAccessToken(final String verifierStr) {
        return Single.create(new Single.OnSubscribe<Token>() {
            @SuppressWarnings("TryWithIdenticalCatches")
            @Override
            public void call(SingleSubscriber<? super Token> subscriber) {
                try {
                    oAuthProvider.retrieveAccessToken(oAuthConsumer, verifierStr);
                    Token token =
                            new Token(oAuthConsumer.getToken(), oAuthConsumer.getTokenSecret());
                    subscriber.onSuccess(token);
                } catch (OAuthMessageSignerException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                } catch (OAuthNotAuthorizedException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                } catch (OAuthExpectationFailedException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                } catch (OAuthCommunicationException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        });
    }

    public static String extractVerifier(String dataString) {
        Uri uri = Uri.parse(dataString);
        return uri.getQueryParameter("oauth_verifier");
    }
}
