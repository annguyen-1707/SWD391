package com.example.swd_demo_backend.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {

    public void sendRegistrationNotification(String email, String token) {
        // Mock sending email
        log.info("Sending registration email to: {}", email);
        log.info("Subject: Complete your registration");
        log.info("Content: Your verification token is {}", token);
        log.info("Email sent successfully.");
    }
}
