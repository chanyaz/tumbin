package com.sakuna63.tumblrin.data.model;

import lombok.Getter;

@Getter
public class Token {
    private final String token;
    private final String tokenSecret;

    public Token(String token, String tokenSecret) {
        this.token = token;
        this.tokenSecret = tokenSecret;
    }
}
