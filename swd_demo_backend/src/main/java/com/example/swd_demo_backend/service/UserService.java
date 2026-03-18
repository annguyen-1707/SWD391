package com.example.swd_demo_backend.service;

import com.example.swd_demo_backend.entity.Role;
import com.example.swd_demo_backend.entity.User;
import com.example.swd_demo_backend.repository.RoleRepository;
import com.example.swd_demo_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public void validateUserData(String username, String email) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
    }

    @Transactional
    public User createUser(String username, String email, String password, Long roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password); // In real app, encode this
        user.setStatus("ACTIVE");
        user.setCreatedAt(LocalDateTime.now());
        user.getRoles().add(role);

        return userRepository.save(user);
    }
}
