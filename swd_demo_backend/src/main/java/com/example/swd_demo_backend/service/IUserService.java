package com.example.swd_demo_backend.service;

import com.example.swd_demo_backend.entity.User;

import java.util.Set;

public interface IUserService {
    void validateUserData(String username, String email);
    User createUser(String username, String email, String password, Set<Long> roleIds);
}
