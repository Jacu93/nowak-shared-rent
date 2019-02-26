package com.piekoszek.nowaksharedrent.auth;

import org.springframework.data.repository.Repository;

interface AccountRepository extends Repository<Account, String> {

    boolean existsById(String id);
    void save(Account account);
    Account findOne(String id);
}
