package com.piekoszek.nowaksharedrent.invite;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryInvitationRepository implements InvitationRepository {

    private Map<String, Invitation> map = new ConcurrentHashMap<>();

    @Override
    public boolean existsByReceiverAndApartmentId(String to, String apartment) {
        for (Map.Entry<String, Invitation> entry : map.entrySet()) {
            if (entry.getValue().receiver.equals(to) && entry.getValue().apartmentId.equals(apartment)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void save(Invitation invitation) {
        map.put(invitation.getId(), invitation);
    }

    @Override
    public Invitation findById(String id) {
        return map.get(id);
    }

    @Override
    public void deleteById(String id) {
        map.remove(id);
    }

    @Override
    public List<Invitation> findAllByReceiver(String to) {
        List<Invitation> invitations = new ArrayList<>();

        for (Map.Entry<String, Invitation> entry : map.entrySet()) {
            if (entry.getValue().receiver.equals(to)) {
                invitations.add(entry.getValue());
            }
        }

        return invitations;
    }
}
