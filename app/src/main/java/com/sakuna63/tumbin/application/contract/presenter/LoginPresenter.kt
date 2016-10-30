package com.sakuna63.tumbin.application.contract.presenter

import android.content.Context
import com.sakuna63.tumbin.R
import com.sakuna63.tumbin.application.contract.LoginContract
import com.sakuna63.tumbin.application.contract.presenter.login.OauthHelper
import com.sakuna63.tumbin.application.di.scope.ActivityScope
import com.sakuna63.tumbin.application.misc.AccountManager
import com.sakuna63.tumbin.data.model.Token
import com.trello.rxlifecycle.LifecycleTransformer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

@ActivityScope
class LoginPresenter
@Inject
constructor(private val context: Context,
            private val view: LoginContract.View,
            private val oauthHelper: OauthHelper,
            private val accountManager: AccountManager,
            private val transformer: LifecycleTransformer<Any>) : LoginContract.Presenter {

    @Inject
    internal fun setupView() {
        this.view.setPresenter(this)
    }

    override fun init() {
        view.setLoginButtonActive(true)
        view.setLoginProgress(false, "")
    }

    override fun destroy() {
        // do nothing
    }

    override fun onClickLogin() {
        view.setLoginButtonActive(false)
        view.setLoginProgress(true, context.getString(R.string.msg_fetch_authorization_url))
        getAuthorizationUrl()
    }

    // tumbin://authorize?oauth_token=token&oauth_verifier=verifier#_=_
    override fun onLoginCallback(dataString: String) {
        view.setLoginProgress(true, context.getString(R.string.msg_fetch_authorization_token))

        val verifier = OauthHelper.extractVerifier(dataString)
        if (verifier == null) {
            view.setLoginButtonActive(true)
            view.showErrorMessage("fail to get verifier")
            return
        }
        oauthHelper.getAccessToken(verifier)
                .compose(transformer.forSingle<Token>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    accountManager.saveToken(it)
                    view.navigateToHome()
                }, {
                    view.setLoginButtonActive(true)
                    view.showErrorMessage(it.message!!)
                })
    }

    private fun getAuthorizationUrl() {
        oauthHelper.getAuthorizationUrl()
                .compose(transformer.forSingle<String>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    view.navigateToLoginPage(it)
                }, {
                    view.setLoginButtonActive(true)
                    view.showErrorMessage(it.message!!)
                })
    }
}
