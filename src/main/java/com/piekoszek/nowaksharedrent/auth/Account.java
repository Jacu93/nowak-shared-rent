package com.piekoszek.nowaksharedrent.auth;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;

@Builder
@Getter

class Account {

    @Id
    private String email;
    private String name;
    private String password;


    public Account(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }
}
