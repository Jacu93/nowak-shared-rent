package com.piekoszek.nowaksharedrent.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class JwtData {

    private String email;
    private String name;
}
