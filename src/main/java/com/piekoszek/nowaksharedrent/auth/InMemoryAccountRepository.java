package com.piekoszek.nowaksharedrent.auth;

import com.piekoszek.nowaksharedrent.invite.Invitation;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryAccountRepository implements AccountRepository {
    private Map<String, Account> map = new ConcurrentHashMap<>();

    @Override
    public void save(Account account) {
        map.put(account.getEmail(), account);
    }

    @Override
    public boolean existsByEmail(String email) {
        return map.containsKey(email);
    }

    @Override
    public Account findByEmail(String email) {
        return map.get(email);
    }

    @Override
    public Account findByResetPasswordKey(String resetPasswordKey) {

        for (Map.Entry<String, Account> entry : map.entrySet()) {

            if (entry.getValue().resetPasswordKey.equals(resetPasswordKey)) {

                return entry.getValue();
            }
        }
        return null;
    }
}
