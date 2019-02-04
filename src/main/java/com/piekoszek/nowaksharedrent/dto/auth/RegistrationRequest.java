package com.piekoszek.nowaksharedrent.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class RegistrationRequest {
    private String email;
    private String name;
    private String password;
}
