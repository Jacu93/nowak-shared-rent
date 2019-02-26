package com.piekoszek.nowaksharedrent.auth;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryAccountRepository implements AccountRepository {
    private Map<String, Account> map = new ConcurrentHashMap<>();

    @Override
    public void save(Account account) {
        map.put(account.getEmail(), account);
    }

    @Override
    public boolean existsById(String id) {
        return map.containsKey(id);
    }

    @Override
    public Account findByEmail(String email) {
        return map.get(email);
    }
}
