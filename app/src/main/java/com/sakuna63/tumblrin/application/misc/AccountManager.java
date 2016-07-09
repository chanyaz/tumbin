package com.sakuna63.tumblrin.application.misc;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sakuna63.tumblrin.application.preferences.TokenPrefs;

import org.scribe.model.Token;

import javax.inject.Inject;

public class AccountManager {

    @NonNull
    private final Context context;

    @Inject
    public AccountManager(@NonNull Context context) {
        this.context = context;
    }

    public boolean isLogined() {
        return getToken() != null;
    }

    @Nullable
    public Token getToken() {
        TokenPrefs prefs = TokenPrefs.get(context);
        if (!prefs.hasToken() || !prefs.hasTokenSecret()) {
            return null;
        }

        return new Token(
                prefs.getToken(),
                prefs.getTokenSecret()
        );
    }

    public void saveToken(@NonNull Token token) {
        TokenPrefs prefs = TokenPrefs.get(context);
        prefs.putToken(token.getToken());
        prefs.putToken(token.getSecret());
    }

    public void removeToken() {
        TokenPrefs tokenPrefs = TokenPrefs.get(context);
        tokenPrefs.removeToken();
        tokenPrefs.removeTokenSecret();
    }
}
