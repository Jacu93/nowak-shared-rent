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
    private List<Rent> rents;

    public Apartment(String id, String address, String city, String admin) {
        this.id = id;
        this.address = address;
        this.city = city;
        this.admin = admin;
        this.tenants = new HashSet<>();
        this.rents = new ArrayList<>();
    }

    void addTenant(Tenant tenant) {
        tenants.add(tenant);
    }

    boolean hasTenant(String email) {
        return tenants.stream().anyMatch(t-> t.getEmail().equals(email));
    }

    void updateRent(Rent newRent) {
        this.rents.add(newRent);
    }
}
