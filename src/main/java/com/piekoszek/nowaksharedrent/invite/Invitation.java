package com.piekoszek.nowaksharedrent.invite;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Invitation {

    String sender;
    String receiver;
    String apartmentId;
    String apartmentName;
    boolean isAccepted;
}
