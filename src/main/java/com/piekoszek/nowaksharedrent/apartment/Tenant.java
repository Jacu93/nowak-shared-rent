package com.piekoszek.nowaksharedrent.apartment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Tenant {

    @Id
    private String email;
    private String name;

    @Override
    public int hashCode() {
        return email.hashCode();
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
        Tenant other = (Tenant) obj;
        return email.equals(other.email);
    }


}
