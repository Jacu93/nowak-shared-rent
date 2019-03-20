package com.piekoszek.nowaksharedrent.hash;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
class HashServiceImpl implements HashService {

    @Override
    public String encrypt(String text) {
        return BCrypt.hashpw(text, BCrypt.gensalt());
    }

    @Override
    public boolean compareWithHash(String text, String hashText) {
        return BCrypt.checkpw(text, hashText);
    }
}