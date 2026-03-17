package com.running_platform.service.impl;

import com.running_platform.constant.ErrorEnum;
import com.running_platform.enums.RoleEnum;
import com.running_platform.dto.request.UserRequest;
import com.running_platform.dto.response.UserResponse;
import com.running_platform.entity.UserAuth.Roles;
import com.running_platform.entity.UserAuth.Users;

import com.running_platform.entity.UserAuth.VerificationTokens;
import com.running_platform.exception.AppException;
import com.running_platform.mapper.RoleMapper;
import com.running_platform.mapper.UserMapper;
import com.running_platform.repository.*;
import com.running_platform.service.EmailService;
import com.running_platform.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j(topic = "USER_SERVICE")
public class UserServiceImpl implements UserService {
    UserRepository repository;
    RoleRepository roleRepository;
    UserMapper mapper;
    RoleMapper roleMapper;
    PasswordEncoder passwordEncoder;
    VerificationTokenRepository tokenRepository;
    EmailService emailService;
    UserRepository userRepository;

    @Transactional
    public UserResponse register(UserRequest userRegister) {
        if (repository.existsByUsername(userRegister.getUsername())) {
            throw new AppException(ErrorEnum.USERNAME_EXIST);
        }
        Set<Roles> roles = new HashSet<>();
        Roles roleUser = roleRepository.findByRoleName(RoleEnum.USER);
        roles.add(roleUser);
        Users user = Users.builder()
                .username(userRegister.getUsername())
                .password(passwordEncoder.encode(userRegister.getPassword()))
                .roles(roles)
                .emailVerified(false)
                .registeredProviderId(userRegister.getRegisteredProviderId())
                .fullName(userRegister.getFullName())
                .emailVerified(userRegister.isEmailVerified())
                .phoneNumber(userRegister.getPhoneNumber())
                .build();

        user = repository.save(user);

        if (!userRegister.isEmailVerified()) {
            sendVerificationLink(user);
        }


        return mapper.toUserResponse(user);
    }

    private void sendVerificationLink(Users user) {
        String token = UUID.randomUUID().toString();

        VerificationTokens verificationToken = VerificationTokens.builder()
                .user(user)
                .token(token)
                .expiryDate(LocalDateTime.now().plusMinutes(15))
                .build();

        tokenRepository.save(verificationToken);
        String content = "Click this link to verify: " + "http://localhost:8080/auth/verify?token=" + token;
        String subject = "Email Verification";
        emailService.sendVerificationEmail(user.getUsername(), content, subject);
    }

    @Override
    public UserResponse getUserByUsername(String username) {
        Users user = repository.findByUsername(username).orElseThrow(() -> new AppException(ErrorEnum.UNKNOWN_ERROR));
        UserResponse userResponse = mapper.toUserResponse(user);
        userResponse.setRoles(roleMapper.toResponse(user.getRoles()));
        return userResponse;
    }
}
