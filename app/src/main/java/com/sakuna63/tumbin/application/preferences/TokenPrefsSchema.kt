package com.sakuna63.tumbin.application.preferences

import com.rejasupotaro.android.kvs.annotations.Key
import com.rejasupotaro.android.kvs.annotations.Table

@Suppress("unused")
@Table(name = "token")
class TokenPrefsSchema {
    @Key(name = "token")
    internal var token: String? = null
    @Key(name = "token_secret")
    internal var tokenSecret: String? = null
}
