package com.sakuna63.tumbin.application.contract;

import android.support.annotation.NonNull;

public interface LoginContract {
    interface View extends BaseView<Presenter> {

        void setLoginButtonActive(boolean active);

        void setLoginProgress(boolean visible, @NonNull String message);

        void showErrorMessage(@NonNull String message);

        void navigateToHome();

        void navigateToLoginPage(@NonNull String url);
    }

    interface Presenter extends BasePresenter {
        void onClickLogin();

        void onLoginCallback(@NonNull String dataString);
    }
}