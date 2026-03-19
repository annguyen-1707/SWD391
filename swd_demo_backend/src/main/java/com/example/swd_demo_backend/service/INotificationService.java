package com.example.swd_demo_backend.service;

public interface INotificationService {
    void sendRegistrationNotification(String email, String subject, String content);
}
