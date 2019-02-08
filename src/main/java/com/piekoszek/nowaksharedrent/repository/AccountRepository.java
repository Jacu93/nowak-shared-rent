package com.piekoszek.nowaksharedrent.repository;

import com.piekoszek.nowaksharedrent.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account, String> {

}
