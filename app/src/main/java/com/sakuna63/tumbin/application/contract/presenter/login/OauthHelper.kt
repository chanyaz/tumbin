package com.sakuna63.tumbin.application.contract.presenter.login

import android.net.Uri
import com.sakuna63.tumbin.BuildConfig
import com.sakuna63.tumbin.data.model.Token
import oauth.signpost.OAuthConsumer
import oauth.signpost.OAuthProvider
import rx.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OauthHelper
@Inject
constructor(private val oAuthConsumer: OAuthConsumer, private val oAuthProvider: OAuthProvider) {

    fun getAuthorizationUrl(): Single<String> =
            Single.fromCallable {
                oAuthProvider.retrieveRequestToken(oAuthConsumer, BuildConfig.CALLBACK_URL)
            }

    fun getAccessToken(verifierStr: String): Single<Token> =
            Single.fromCallable {
                oAuthProvider.retrieveAccessToken(oAuthConsumer, verifierStr)
                Token(oAuthConsumer.token, oAuthConsumer.tokenSecret)
            }

    companion object {
        fun extractVerifier(dataString: String): String? {
            val uri = Uri.parse(dataString)
            return uri.getQueryParameter("oauth_verifier")
        }
    }
}
