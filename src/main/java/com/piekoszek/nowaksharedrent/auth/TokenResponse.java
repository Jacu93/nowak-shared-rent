package com.piekoszek.nowaksharedrent.auth;

import lombok.Getter;

@Getter
class TokenResponse {

    String token;

    TokenResponse (String tokenToReturn) {
        this.token = tokenToReturn;
    }
}
