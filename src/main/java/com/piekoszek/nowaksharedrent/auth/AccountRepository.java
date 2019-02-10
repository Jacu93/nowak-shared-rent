package com.piekoszek.nowaksharedrent.auth;

import org.springframework.data.mongodb.repository.MongoRepository;

interface AccountRepository extends MongoRepository<Account, String> {

}
