package com.piekoszek.nowaksharedrent.auth;

import lombok.Getter;

@Getter
class TokenResponse {

    private String token;

    TokenResponse (String tokenToReturn) {
        this.token = tokenToReturn;
    }
}
