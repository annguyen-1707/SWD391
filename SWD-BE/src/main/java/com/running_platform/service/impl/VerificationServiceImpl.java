package com.running_platform.service.impl;

import com.running_platform.entity.UserAuth.Users;
import com.running_platform.entity.UserAuth.VerificationTokens;
import com.running_platform.repository.VerificationTokenRepository;
import com.running_platform.service.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService {

    private final VerificationTokenRepository tokenRepository;

    @Override
    public VerificationTokens createToken(Users user) {
        String token = UUID.randomUUID().toString();

        VerificationTokens verificationToken = VerificationTokens.builder()
                .user(user)
                .token(token)
                .expiryDate(LocalDateTime.now().plusMinutes(15))
                .build();

        return tokenRepository.save(verificationToken);
    }
}
