package com.piekoszek.nowaksharedrent.apartment;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.Collection;

@Getter
@NoArgsConstructor
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

    void setOneTenant(Tenant tenant) {
        this.tenants.add(tenant);
    }

    boolean getOneTenant(String email) {
        while (this.tenants.iterator().hasNext()) {
            if (this.tenants.iterator().next().getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }
}
