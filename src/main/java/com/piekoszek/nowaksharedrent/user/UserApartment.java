package com.piekoszek.nowaksharedrent.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@Getter
@Builder
public class UserApartment {

    @Id
    String id;
    String name;
    boolean isOwner;

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        UserApartment other = (UserApartment) obj;
        return id.equals(other.id);
    }
}