package com.piekoszek.nowaksharedrent.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.Id;

import java.util.Set;

@Builder
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class User {

    @Id
    private String email;
    private String name;
    private Set<UserApartment> apartments;

    void addApartment(UserApartment userApartment) {
        apartments.add(userApartment);
    }
}
