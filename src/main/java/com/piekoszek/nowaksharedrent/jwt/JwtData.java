package com.piekoszek.nowaksharedrent.jwt;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder

public class JwtData {

    private String email;
    private String name;
}
