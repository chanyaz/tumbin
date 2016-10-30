package com.sakuna63.tumbin.application.misc

import android.content.Context

import com.sakuna63.tumbin.application.preferences.TokenPrefs
import com.sakuna63.tumbin.application.util.PostUtils
import com.sakuna63.tumbin.data.model.Token

import javax.inject.Inject

class AccountManager
@Inject
constructor(private val context: Context) {

    val isLoggedIn: Boolean
        get() = token != null

    val token: Token?
        get() {
            val prefs = TokenPrefs.get(context)
            if (!prefs.hasToken() || !prefs.hasTokenSecret()) {
                return null
            }

            return Token(
                    prefs.token,
                    prefs.tokenSecret)
        }

    fun saveToken(token: Token) {
        val prefs = TokenPrefs.get(context)
        prefs.putToken(token.token)
        prefs.putTokenSecret(token.tokenSecret)
    }

    fun removeToken() {
        val tokenPrefs = TokenPrefs.get(context)
        tokenPrefs.removeToken()
        tokenPrefs.removeTokenSecret()
    }
}
