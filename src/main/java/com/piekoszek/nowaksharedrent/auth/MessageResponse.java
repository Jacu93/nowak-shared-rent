package com.piekoszek.nowaksharedrent.auth;

import lombok.Getter;

@Getter
class MessageResponse {

    private String message;

    MessageResponse(String msg) {
        this.message=msg;
    }
}
