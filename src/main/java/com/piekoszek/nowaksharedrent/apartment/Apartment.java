package com.piekoszek.nowaksharedrent.apartment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Collection;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
class Apartment {

    @Id
    private String id;
    private String address;
    private String city;
    private String admin;
    private Collection<Tenant> tenants;

    Apartment(String id, String address, String city, String admin) {
        this.id = id;
        this.address = address;
        this.city = city;
        this.admin = admin;
    }
}
