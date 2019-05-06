package com.piekoszek.nowaksharedrent.jwt;

import com.piekoszek.nowaksharedrent.dto.UserApartment;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Date;
import java.util.Set;

@Getter
@Builder
@EqualsAndHashCode
public class JwtData {

    private String email;
    private String name;
    private Date exp;
    private Set<UserApartment> apartments;
}
