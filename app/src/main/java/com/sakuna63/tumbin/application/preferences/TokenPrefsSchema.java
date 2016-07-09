package com.sakuna63.tumbin.application.preferences;

import com.rejasupotaro.android.kvs.annotations.Key;
import com.rejasupotaro.android.kvs.annotations.Table;

@Table(name = "token")
public class TokenPrefsSchema {
    @Key(name = "token")
    String token;
    @Key(name = "token_secret")
    String tokenSecret;
}
