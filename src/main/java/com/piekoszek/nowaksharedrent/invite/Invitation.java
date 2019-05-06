package com.piekoszek.nowaksharedrent.invite;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;

@Builder
@Getter
public class Invitation {

    @Id
    String id;
    String sender;
    String receiver;
    String apartmentId;
    String apartmentName;
}
