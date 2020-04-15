package com.piekoszek.nowaksharedrent.email;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Email {

    private String to;
    private String subject;
    private String text;
}
