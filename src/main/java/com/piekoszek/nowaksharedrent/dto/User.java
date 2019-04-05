package com.piekoszek.nowaksharedrent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.Id;

import java.util.HashSet;

@Builder
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class User {

    @Id
    private String email;
    private String name;
    private HashSet<UserApartment> apartments;

    public void addApartment(UserApartment userApartment) {
        apartments.add(userApartment);
    }
}
