package com.piekoszek.nowaksharedrent.email;

public interface EmailService {

    void sendSimpleMessage(String to, String subject, String text);
}
