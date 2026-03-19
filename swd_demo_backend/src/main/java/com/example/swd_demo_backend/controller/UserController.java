package com.example.swd_demo_backend.controller;

import com.example.swd_demo_backend.entity.User;
import com.example.swd_demo_backend.entity.VerificationToken;
import com.example.swd_demo_backend.repository.UserRepository;
import com.example.swd_demo_backend.service.NotificationService;
import com.example.swd_demo_backend.service.UserRoleService;
import com.example.swd_demo_backend.service.UserService;
import com.example.swd_demo_backend.service.VerificationTokenService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // For demo purposes
public class UserController {

    private final UserService userService;
    private final VerificationTokenService verificationTokenService;
    private final NotificationService notificationService;
    private final UserRoleService userRoleService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest request) {
        try {
            // Step 1: Validate User Data
            userService.validateUserData(request.getUsername(), request.getEmail());

            // Step 2: Create User
            User user = userService.createUser(
                    request.getUsername(),
                    request.getEmail(),
                    request.getPassword(),
                    request.getRoleIds()
            );

            // Step 3: Create Verification Token
            VerificationToken token = verificationTokenService.createToken(user.getId());

            // Step 4: Send Notification
            String content = "Click this link to verify: " + "http://localhost:8080/auth/verify?token=" + token;
            String subject = "Email Verification";
            notificationService.sendRegistrationNotification(user.getEmail(), subject, content);

            return ResponseEntity.ok(new MessageResponse("User created successfully. Verification email sent."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new MessageResponse("An error occurred during user creation."));
        }
    }

    @PostMapping("/V2")
    public ResponseEntity<?> createUserV2(@RequestBody CreateUserRequest request) {
        try {
            // Step 1: Validate User Data
            userService.validateUserData(request.getUsername(), request.getEmail());

            // Step 2: Create User
            User user = userService.createUserV2(
                    request.getUsername(),
                    request.getEmail(),
                    request.getPassword());

            // Step 3: update role
            userRoleService.assignRole(user.getId(), request.getRoleIds());

            // Step 4: Create Verification Token
            VerificationToken token = verificationTokenService.createToken(user.getId());

            // Step 5: Send Notification
            String content = "Click this link to verify: " + "http://localhost:8080/auth/verify?token=" + token;
            String subject = "Email Verification";
            notificationService.sendRegistrationNotification(user.getEmail(), subject, content);

            return ResponseEntity.ok(new MessageResponse("User created successfully. Verification email sent."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new MessageResponse("An error occurred during user creation."));
        }
    }

    @Data
    public static class CreateUserRequest {
        private String username;
        private String email;
        private String password;
        Set<Long> roleIds;
    }

    @Data
    public static class MessageResponse {
        private final String message;
    }
}
