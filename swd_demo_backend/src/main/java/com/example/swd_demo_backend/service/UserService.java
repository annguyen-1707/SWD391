package com.example.swd_demo_backend.service;

import com.example.swd_demo_backend.entity.Role;
import com.example.swd_demo_backend.entity.User;
import com.example.swd_demo_backend.repository.RoleRepository;
import com.example.swd_demo_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

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
    public User createUser(String username, String email, String password, Set<Long> roleIds) {
        Set<Role> roles = new HashSet<>();
        roleIds.forEach(roleId -> {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new IllegalArgumentException("Role not found"));
            roles.add(role);
        });

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password); // In real app, encode this
        user.setStatus("NOT_VERIFIED");
        user.setCreatedAt(LocalDateTime.now());
        user.setRoles(roles);

        return userRepository.save(user);
    }

    @Transactional
    public User createUserV2(String username, String email, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password); // In real app, encode this
        user.setStatus("NOT_VERIFIED");
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }
}
