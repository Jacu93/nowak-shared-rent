package com.piekoszek.nowaksharedrent.auth;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;

@Builder
@Getter

class Account {

    @Id
    private String email;
    private String password;
    private String name;
}
