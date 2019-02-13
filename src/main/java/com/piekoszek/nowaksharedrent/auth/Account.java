package com.piekoszek.nowaksharedrent.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;

@Builder
@Getter
@AllArgsConstructor

class Account {

    @Id
    private String email;
    private String name;
    private String password;
}
