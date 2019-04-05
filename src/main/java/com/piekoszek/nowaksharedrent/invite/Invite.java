package com.piekoszek.nowaksharedrent.invite;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
class Invite {

    String sender;
    String receiver;
    String apartmentId;
}
