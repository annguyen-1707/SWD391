package com.running_platform.service;

public interface NotificationService {
    void sendRegistrationNotification(String email, String content, String subject);
}
