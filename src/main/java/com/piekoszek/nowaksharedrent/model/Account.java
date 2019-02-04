package com.piekoszek.nowaksharedrent.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Builder
@Getter
@Setter

public class Account {
    @Id
    private String email;
    private String password;
    private String name;
    private String id;
}
