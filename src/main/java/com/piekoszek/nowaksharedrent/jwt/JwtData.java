package com.piekoszek.nowaksharedrent.jwt;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode

public class JwtData {

    private String email;
    private String name;
}
