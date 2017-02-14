package com.sakuna63.tumbin.application.misc

import com.sakuna63.tumbin.application.preferences.TokenPref
import com.sakuna63.tumbin.data.model.Token
import javax.inject.Inject

class AccountManager
@Inject constructor() {

    val isLoggedIn: Boolean
        get() = token != null

    val token: Token?
        get() {
            val token = TokenPref.token
            val tokenSecret = TokenPref.tokenSecret
            if (token.isNullOrBlank() || tokenSecret.isNullOrBlank()) {
                return null
            }

            return Token(token!!, tokenSecret!!)
        }

    fun saveToken(token: Token) {
        TokenPref.token = token.token
        TokenPref.tokenSecret = token.tokenSecret
    }

    fun removeToken() {
        TokenPref.token = null
        TokenPref.tokenSecret = null
    }
}
