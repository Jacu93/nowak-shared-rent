package com.piekoszek.nowaksharedrent.auth;

import lombok.Getter;

@Getter
class MessageResponse {

    String message;

    MessageResponse(String msg) {
        this.message=msg;
    }
}
