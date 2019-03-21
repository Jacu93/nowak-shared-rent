package com.piekoszek.nowaksharedrent.apartment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;

import java.util.Collection;

@Builder
@Getter
@AllArgsConstructor
class Apartment {

    @Id
    private String id;
    private String address;
    private String city;
    private String admin;
    private Collection<Tenant> tenants;
}
