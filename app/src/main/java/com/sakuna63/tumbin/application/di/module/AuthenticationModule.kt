package com.sakuna63.tumbin.application.di.module

import android.content.Context
import com.sakuna63.tumbin.BuildConfig
import com.sakuna63.tumbin.application.misc.AccountManager
import dagger.Module
import dagger.Provides
import oauth.signpost.OAuthConsumer
import oauth.signpost.OAuthProvider
import oauth.signpost.basic.DefaultOAuthProvider
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer
import javax.inject.Singleton

@Module
class AuthenticationModule {

    @Provides
    @Singleton
    fun oAuthProvider(): OAuthProvider = DefaultOAuthProvider(
            REQUEST_TOKEN_RESOURCE,
            ACCESS_TOKEN_RESOURCE,
            AUTHORIZE_URL)

    @Provides
    @Singleton
    fun oAuthConsumer(accountManager: AccountManager): OAuthConsumer {
        val consumer = OkHttpOAuthConsumer(BuildConfig.CONSUMER_KEY, BuildConfig.CONSUMER_KEY_SECRET)
        if (accountManager.isLoggedIn) {
            val token = accountManager.token
            //noinspection ConstantConditions
            consumer.setTokenWithSecret(token!!.token, token.tokenSecret)
        }
        return consumer
    }

    @Singleton
    @Provides
    fun accountManager(context: Context): AccountManager = AccountManager(context)

    companion object {
        private val AUTHORIZE_URL = "https://www.tumblr.com/oauth/authorize?oauth_token=%s"
        private val REQUEST_TOKEN_RESOURCE = "http://www.tumblr.com/oauth/request_token"
        private val ACCESS_TOKEN_RESOURCE = "http://www.tumblr.com/oauth/access_token"
    }
}
