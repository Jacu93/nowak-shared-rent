package com.piekoszek.nowaksharedrent.invite;

import java.util.List;
import java.util.Optional;

public interface InviteService {

    void createInvitation(String from, String to, String apartment);
    Optional<String> resolveInvitation(String to, String apartment, boolean isAccepted);
    List<Invitation> getInvitations(String to);
}
