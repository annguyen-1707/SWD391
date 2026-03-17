package com.running_platform.service;

import com.running_platform.dto.request.ResetPasswordRequest;
import com.running_platform.dto.request.UserRequest;
import com.running_platform.dto.response.UserResponse;
import org.apache.catalina.User;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserResponse register(UserRequest userRegister);
    UserResponse getUserByUsername(String username);


}
