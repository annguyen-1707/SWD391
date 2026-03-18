package com.running_platform.service;

import com.running_platform.entity.UserAuth.Users;
import com.running_platform.entity.UserAuth.VerificationTokens;

public interface VerificationService {
    VerificationTokens createToken(Users user);
}
