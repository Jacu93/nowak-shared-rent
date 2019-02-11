package com.piekoszek.nowaksharedrent.auth;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryAccountRepository implements AccountRepository {
    private ConcurrentHashMap<String, Account> inMemoryAccountRepository = new ConcurrentHashMap();

    @Override
    public void save(Account account) {
        inMemoryAccountRepository.put(account.getEmail(), account);
    }

    @Override
    public boolean existsById(String id) {
        Account account = inMemoryAccountRepository.get(id);
        return (!Objects.isNull(account));
    }
}
