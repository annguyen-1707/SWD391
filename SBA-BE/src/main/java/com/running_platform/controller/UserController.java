package com.running_platform.controller;


import com.running_platform.dto.request.UserRequest;
import com.running_platform.dto.response.ApiResponse;
import com.running_platform.dto.response.UserResponse;
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

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Object>> register(@Valid @RequestBody UserRequest userRegister) {
        return ResponseEntity.ok()
                .body(ApiResponse.<Object>builder()
                        .code(200)
                        .status("OK")
                        .message("User register in successfully")
                        .data(service.register(userRegister))
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
}
