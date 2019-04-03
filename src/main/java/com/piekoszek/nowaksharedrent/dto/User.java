package com.piekoszek.nowaksharedrent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;

@Builder
@Getter
@AllArgsConstructor
public class User {

    @Id
    private String email;
    private String name;
}
