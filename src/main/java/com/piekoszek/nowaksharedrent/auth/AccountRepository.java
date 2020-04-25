package com.piekoszek.nowaksharedrent.auth;

import org.springframework.data.repository.Repository;

interface AccountRepository extends Repository<Account, String> {

    boolean existsByEmail(String email);
    void save(Account account);
    Account findByEmail(String email);
    Account findByResetPasswordKey(String resetPasswordKey);
}
