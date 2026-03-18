package com.running_platform.config;


import com.running_platform.enums.RoleEnum;
import com.running_platform.entity.UserAuth.Roles;
import com.running_platform.entity.UserAuth.Users;
import com.running_platform.repository.RoleRepository;
import com.running_platform.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Configuration
public class ApplicationInitConfig {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner initApplicationRunner(UserRepository userRepository, RoleRepository roleRepository) {
        return args -> {
            if (userRepository.findByUsername(("admin")).isEmpty()) {
                Set<Roles> roles = new HashSet<Roles>();
                Roles platformAdmin = roleRepository.findByRoleName(RoleEnum.PLATFORM_ADMIN);
                if (platformAdmin == null) {
                    platformAdmin = Roles.builder().roleName(RoleEnum.PLATFORM_ADMIN).build();
                    roleRepository.save(platformAdmin);
                }
                Roles contentAdmin = roleRepository.findByRoleName(RoleEnum.CONTENT_ADMIN);
                if (contentAdmin == null) {
                    contentAdmin = Roles.builder().roleName(RoleEnum.CONTENT_ADMIN).build();
                    roleRepository.save(contentAdmin);
                }
                Roles learner = roleRepository.findByRoleName(RoleEnum.LEARNER);
                if (learner == null) {
                    learner = Roles.builder().roleName(RoleEnum.LEARNER).build();
                    roleRepository.save(learner);
                }
                Roles courseAuthor = roleRepository.findByRoleName(RoleEnum.COURSE_AUTHOR);
                if (courseAuthor == null) {
                    courseAuthor = Roles.builder().roleName(RoleEnum.COURSE_AUTHOR).build();
                    roleRepository.save(courseAuthor);
                }
                roles.add(platformAdmin);
                Users user = Users.builder().username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .roles(roles)
                        .emailVerified(true)
                        .email("gasoqua1707@gmail.com")
                        .build();
                userRepository.save(user);
                log.warn("Initialized admin user with username: admin and password: admin");
            }
        };
    }
}
