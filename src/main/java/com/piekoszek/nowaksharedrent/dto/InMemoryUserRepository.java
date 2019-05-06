package com.piekoszek.nowaksharedrent.dto;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryUserRepository implements UserRepository {
    private Map<String, User> map = new ConcurrentHashMap<>();

    @Override
    public void save(User user) {
        map.put(user.getEmail(), user);
    }

    @Override
    public boolean existsByEmail(String email) {
        return map.containsKey(email);
    }

    @Override
    public User findByEmail(String email) {
        return map.get(email);
    }
}