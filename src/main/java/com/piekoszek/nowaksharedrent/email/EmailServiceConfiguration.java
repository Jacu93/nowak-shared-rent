package com.piekoszek.nowaksharedrent.email;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class EmailServiceConfiguration {

    @Bean
    EmailService emailService (JavaMailSender javaMailSender) {

        return new EmailServiceImpl(javaMailSender);
    }
}
