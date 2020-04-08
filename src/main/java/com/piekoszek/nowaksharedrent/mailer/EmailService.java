package com.piekoszek.nowaksharedrent.mailer;

public interface EmailService {

    void sendSimpleMessage(String to, String subject, String text);
}
