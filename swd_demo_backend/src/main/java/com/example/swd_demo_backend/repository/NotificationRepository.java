package com.example.swd_demo_backend.repository;

import com.example.swd_demo_backend.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
