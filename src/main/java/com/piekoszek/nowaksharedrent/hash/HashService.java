package com.piekoszek.nowaksharedrent.hash;

public interface HashService {

    String encryptString (String input);
    Boolean compareWithHash (String input, String hashInput);
}
