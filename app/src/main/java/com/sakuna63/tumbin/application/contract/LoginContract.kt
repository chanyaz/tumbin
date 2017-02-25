package com.sakuna63.tumbin.application.contract

interface LoginContract {
    interface View : BaseView<Presenter> {

        fun setLoginButtonActive(active: Boolean)

        fun setLoginProgress(visible: Boolean, message: String)

        fun showErrorMessage(message: String)

        fun navigateToHome()

        fun navigateToLoginPage(url: String)
    }

    interface Presenter : BasePresenter {
        fun onClickLogin()

        fun onLoginCallback(dataString: String?)
    }
}
