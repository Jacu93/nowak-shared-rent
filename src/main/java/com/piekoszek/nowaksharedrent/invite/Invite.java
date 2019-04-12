package com.piekoszek.nowaksharedrent.invite;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Invite {

    String sender;
    String receiver;
    String apartmentId;
    String apartmentName;
}
