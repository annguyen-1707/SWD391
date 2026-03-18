package com.example.swd_demo_backend.controller;

import com.example.swd_demo_backend.entity.User;
import com.example.swd_demo_backend.entity.VerificationToken;
import com.example.swd_demo_backend.service.NotificationService;
import com.example.swd_demo_backend.service.UserService;
import com.example.swd_demo_backend.service.VerificationTokenService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // For demo purposes
public class UserController {

    private final UserService userService;
    private final VerificationTokenService verificationTokenService;
    private final NotificationService notificationService;

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
                    request.getRoleId()
            );

            // Step 3: Create Verification Token
            VerificationToken token = verificationTokenService.createToken(user.getId());

            // Step 4: Send Notification
            notificationService.sendRegistrationNotification(user.getEmail(), token.getToken());

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
        private Long roleId;
    }

    @Data
    public static class MessageResponse {
        private final String message;
    }
}
