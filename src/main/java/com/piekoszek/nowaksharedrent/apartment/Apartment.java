package com.piekoszek.nowaksharedrent.apartment;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.*;

@Getter
public class Apartment {

    @Id
    private String id;
    private String address;
    private String city;
    private String admin;
    private Set<Tenant> tenants;

    public Apartment(String id, String address, String city, String admin) {
        this.id = id;
        this.address = address;
        this.city = city;
        this.admin = admin;
        this.tenants = new HashSet<>();
    }

    void addTenant(Tenant tenant) {
        tenants.add(tenant);
    }

    boolean hasTenant(String email) {
        return tenants.stream().anyMatch(t-> t.getEmail().equals(email));
    }

    void updateBalance(Set<String> excluded, int value) {
        for (Tenant tenant : this.tenants) {
            if (!excluded.contains(tenant.getEmail())) {
                tenant.updateBalance(value);
            }
        }
    }
}
