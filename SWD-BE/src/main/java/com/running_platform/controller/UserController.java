package com.running_platform.controller;


import com.running_platform.dto.request.UserRequest;
import com.running_platform.dto.response.ApiResponse;
import com.running_platform.dto.response.UserResponse;
import com.running_platform.entity.UserAuth.Users;
import com.running_platform.entity.UserAuth.VerificationTokens;
import com.running_platform.mapper.UserMapper;
import com.running_platform.service.NotificationService;
import com.running_platform.service.VerificationService;
import com.running_platform.service.impl.UserServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl service;
    private final VerificationService verificationService;
    private final NotificationService notificationService;
    private final UserMapper userMapper;

    @PostMapping()
    public ResponseEntity<ApiResponse<Object>> createUser(@Valid @RequestBody UserRequest userRegister) {
        Users user = service.createUser(userRegister);
        VerificationTokens token = verificationService.createToken(user);
        String content = "Click this link to verify: " + "http://localhost:8080/auth/verify?token=" + token;
        String subject = "Email Verification";
        notificationService.sendRegistrationNotification(user.getEmail(), content, subject);
        return ResponseEntity.ok()
                .body(ApiResponse.<Object>builder()
                        .code(200)
                        .status("OK")
                        .message("User register in successfully")
                        .data(userMapper.toUserResponse(user))
                        .build());
    }

    @GetMapping("/my-info")
    ApiResponse<UserResponse> getMyInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return ApiResponse.<UserResponse>builder()
                .data(service.getUserByUsername(username))
                .code(200)
                .status("Get Information Successful")
                .build();
    }

    @GetMapping()
    public ApiResponse<Object> getUsersPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.<Object>builder()
                .data(service.getUsers(page, size))
                .code(200)
                .status("Get Information Successful")
                .build();
    }
}
