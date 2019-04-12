package com.piekoszek.nowaksharedrent.invite;

import java.util.ArrayList;

public interface InviteService {

    void createInvite (String from, String to, String apartment);
    void resolveInvite(String to, String apartment, boolean isAccepted);
    ArrayList<Invite> getInvites (String to);
}
