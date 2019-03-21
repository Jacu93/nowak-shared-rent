package com.piekoszek.nowaksharedrent.apartment;

import org.springframework.data.annotation.Id;

class Tenant {

    @Id
    private String email;
    private String name;

}
