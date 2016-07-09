package com.sakuna63.tumblrin.application.contract;

public interface LoginContract {
    interface View extends BaseView<Presenter> {

        void setLoginButtonActive(boolean active);

        void setLoginProgress(boolean visible);

        void showErrorMessage(String message);

        void navigateToHome();

        void navigateToLoginPage(String url);
    }

    interface Presenter extends BasePresenter {
        void onClickLogin();

        void onLoginCallback(String dataString);
    }
}
