package com.example.swd_demo_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "verification_tokens")
@Getter
@Setter
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    private String token;

    @Column(name = "expiration_time")
    private LocalDateTime expirationTime;

    @Column(name = "is_verified")
    private Boolean isVerified = false;
}
