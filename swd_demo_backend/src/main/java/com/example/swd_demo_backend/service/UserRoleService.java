package com.example.swd_demo_backend.service;

import com.example.swd_demo_backend.entity.Role;
import com.example.swd_demo_backend.entity.User;
import com.example.swd_demo_backend.entity.UserRole;
import com.example.swd_demo_backend.repository.RoleRepository;
import com.example.swd_demo_backend.repository.UserRepository;
import com.example.swd_demo_backend.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserRoleService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    public void assignRole(Long userId, Set<Long> roleIds) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        for (Long roleId : roleIds) {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new RuntimeException("Role not found"));

            UserRole userRole = new UserRole();
            userRole.setUser(user);
            userRole.setRole(role);

            userRoleRepository.save(userRole);
        }
    }
}

