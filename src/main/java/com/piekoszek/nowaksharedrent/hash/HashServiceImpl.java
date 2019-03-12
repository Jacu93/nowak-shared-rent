package com.piekoszek.nowaksharedrent.hash;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
class HashServiceImpl implements HashService {

    @Override
    public String encryptString(String input) {
        return BCrypt.hashpw(input, BCrypt.gensalt());
    }

    @Override
    public Boolean compareWithHash(String input, String hashInput) {
        return BCrypt.checkpw(input, hashInput);
    }
}