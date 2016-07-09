package com.sakuna63.tumblrin.application.contract.presenter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.sakuna63.tumblrin.application.contract.LoginContract;
import com.sakuna63.tumblrin.application.contract.presenter.login.OauthHelper;
import com.sakuna63.tumblrin.application.di.scope.ActivityScope;
import com.sakuna63.tumblrin.application.misc.AccountManager;
import com.trello.rxlifecycle.LifecycleTransformer;

import org.scribe.model.Token;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

@ActivityScope
public class LoginPresenter implements LoginContract.Presenter {

    @NonNull
    private final Context context;

    @NonNull
    private final LoginContract.View view;

    @NonNull
    private final OauthHelper oauthHelper;

    @NonNull
    private final AccountManager accountManager;

    private LifecycleTransformer transformer;

    @Inject
    public LoginPresenter(@NonNull Context context,
                          @NonNull LoginContract.View view,
                          @NonNull OauthHelper oauthHelper,
                          @NonNull AccountManager accountManager) {
        this.context = context;
        this.view = view;
        this.oauthHelper = oauthHelper;
        this.accountManager = accountManager;
    }

    @Inject
    void setupView() {
        this.view.setPresenter(this);
    }

    @Override
    public void init(@NonNull LifecycleTransformer transformer) {
        this.transformer = transformer;

        view.setLoginButtonActive(true);
        view.setLoginProgress(false);
        view.showErrorMessage("");
    }

    @Override
    public void onClickLogin() {
        view.setLoginButtonActive(false);
        view.setLoginProgress(true);
        view.showErrorMessage("");
        getAuthorizationUrl();
    }

    @Override
    public void onLoginCallback(String dataString) {
        Log.d("LoginPresenter", dataString);
        // tumblrin://authorize?oauth_token=WYXFqUqMRPi1v397EL7YJ7v0ZpoJSaCCAeQ57AvnxdIMHzrsIp&oauth_verifier=shP0lslZlcbD0CENHoY9hwKoJt8p0swuegsqAcIiW53mylshUS#_=_
        String verifier = oauthHelper.extractVerifier(dataString);
        //noinspection unchecked
        oauthHelper.getAccessToken(verifier)
                .compose(transformer.forSingle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<Token>() {
                    @Override
                    public void call(Token token) {
                        accountManager.saveToken(token);
                        view.navigateToHome();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        view.showErrorMessage(throwable.getMessage());
                    }
                });
    }

    private void getAuthorizationUrl() {
        //noinspection unchecked
        oauthHelper.getAuthorizationUrl()
                .compose(transformer.forSingle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String url) {
                        view.navigateToLoginPage(url);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        view.showErrorMessage(throwable.getMessage());
                    }
                });
    }
}
