package com.example.swd_demo_backend.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {
    private final JavaMailSenderImpl mailSender;

    public NotificationService(JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
    }

    public void sendRegistrationNotification(String email, String subject , String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(content);

        mailSender.send(message);
    }
}
