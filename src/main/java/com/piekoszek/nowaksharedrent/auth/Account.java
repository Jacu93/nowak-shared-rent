package com.piekoszek.nowaksharedrent.auth;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;

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
    String resetPasswordKey;
    //private long resetPasswordValidTo;

    void setResetPasswordKey (String resetPasswordKey) {
        this.resetPasswordKey = resetPasswordKey;
    }

    void setPassword (String password) {
        this.password = password;
    }
}