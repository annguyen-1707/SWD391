package com.example.swd_demo_backend.service;

import com.example.swd_demo_backend.entity.VerificationToken;

public interface IVerificationService {
    VerificationToken createToken(Long userId);
}
