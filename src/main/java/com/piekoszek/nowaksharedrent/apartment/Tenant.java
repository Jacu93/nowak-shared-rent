package com.piekoszek.nowaksharedrent.apartment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@Getter
@Builder
class Tenant {

    @Id
    private String email;
    private String name;

}
