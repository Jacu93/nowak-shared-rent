package com.piekoszek.nowaksharedrent.invite;

public interface InviteService {

    void createInvite (String from, String to, String apartment);
    boolean isAcceptedInvite (String to, String apartment, boolean isAccepted);
}
