package com.piekoszek.nowaksharedrent.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Email;

@Builder
@Getter
@AllArgsConstructor
public class Account {

    @Id
    @Email(message = "Invalid email")
    private String email;

    private String name;

    @Length(min = 4, message = "Too short password")
    private String password;
}