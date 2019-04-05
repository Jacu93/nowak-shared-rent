package com.piekoszek.nowaksharedrent.invite;

public interface InviteService {

    void createInvite (String from, String to, String apartment);
    void resolveInvite(String to, String apartment, boolean isAccepted);
}
