package com.piekoszek.nowaksharedrent.repository;

import com.piekoszek.nowaksharedrent.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AccountRepository extends MongoRepository<Account, String> {
    List<Account> findByEmail (String email);
}
