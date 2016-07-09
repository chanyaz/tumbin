package com.sakuna63.tumbin.application.contract.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.sakuna63.tumbin.R;
import com.sakuna63.tumbin.application.contract.LoginContract;
import com.sakuna63.tumbin.application.di.scope.ActivityScope;
import com.sakuna63.tumbin.application.misc.AccountManager;
import com.sakuna63.tumbin.data.model.Token;
import com.sakuna63.tumbin.application.contract.presenter.login.OauthHelper;
import com.trello.rxlifecycle.LifecycleTransformer;

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

    @NonNull
    private final LifecycleTransformer transformer;

    @Inject
    public LoginPresenter(@NonNull Context context,
                          @NonNull LoginContract.View view,
                          @NonNull OauthHelper oauthHelper,
                          @NonNull AccountManager accountManager,
                          @NonNull LifecycleTransformer transformer) {
        this.context = context;
        this.view = view;
        this.oauthHelper = oauthHelper;
        this.accountManager = accountManager;
        this.transformer = transformer;
    }

    @Inject
    void setupView() {
        this.view.setPresenter(this);
    }

    @Override
    public void init() {
        view.setLoginButtonActive(true);
        view.setLoginProgress(false, "");
    }

    @Override
    public void destroy() {
        // do nothing
    }

    @Override
    public void onClickLogin() {
        view.setLoginButtonActive(false);
        view.setLoginProgress(true, context.getString(R.string.msg_fetch_authorization_url));
        getAuthorizationUrl();
    }

    @Override
    public void onLoginCallback(@NonNull String dataString) {
        Log.d("LoginPresenter", dataString);
        view.setLoginProgress(true, context.getString(R.string.msg_fetch_authorization_token));
        // tumbin://authorize?oauth_token=token&oauth_verifier=verifier#_=_
        String verifier = OauthHelper.extractVerifier(dataString);
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
                        view.setLoginButtonActive(true);
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
                        view.setLoginButtonActive(true);
                        view.showErrorMessage(throwable.getMessage());
                    }
                });
    }
}
