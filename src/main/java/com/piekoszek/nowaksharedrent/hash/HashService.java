package com.piekoszek.nowaksharedrent.hash;

public interface HashService {

    String encrypt(String text);
    boolean compareWithHash (String text, String hashText);
}
