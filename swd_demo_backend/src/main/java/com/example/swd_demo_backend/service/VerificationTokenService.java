package com.example.swd_demo_backend.service;

import com.example.swd_demo_backend.entity.VerificationToken;
import com.example.swd_demo_backend.repository.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VerificationTokenService {

    private final VerificationTokenRepository tokenRepository;

    @Transactional
    public VerificationToken createToken(Long userId) {
        VerificationToken token = new VerificationToken();
        token.setUserId(userId);
        token.setToken(UUID.randomUUID().toString());
        token.setExpirationTime(LocalDateTime.now().plusDays(1)); // 1 day expiration
        token.setIsVerified(false);
        return tokenRepository.save(token);
    }
}
