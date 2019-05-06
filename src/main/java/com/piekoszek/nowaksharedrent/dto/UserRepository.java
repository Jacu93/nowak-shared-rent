package com.piekoszek.nowaksharedrent.dto;

import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<User, String> {

    boolean existsByEmail(String email);
    void save(User user);
    User findByEmail(String email);
}