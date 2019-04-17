package com.piekoszek.nowaksharedrent.apartment;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.*;

@Getter
@NoArgsConstructor
public class Apartment {

    @Id
    private String id;
    private String address;
    private String city;
    private String admin;
    private Set<Tenant> tenants;

    Apartment(String id, String address, String city, String admin) {
        this.id = id;
        this.address = address;
        this.city = city;
        this.admin = admin;
        this.tenants = new HashSet<>();
    }

    void addTenant(Tenant tenant) {
        tenants.add(tenant);
    }

    public boolean hasTenant(String email) {
        return tenants.stream().anyMatch(t-> t.getEmail().equals(email));
    }
}
