package com.piekoszek.nowaksharedrent.jwt;

import com.piekoszek.nowaksharedrent.dto.UserApartment;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.HashSet;

@Getter
@Builder
@EqualsAndHashCode
public class JwtData {

    private String email;
    private String name;
    private HashSet<UserApartment> apartments;
}
