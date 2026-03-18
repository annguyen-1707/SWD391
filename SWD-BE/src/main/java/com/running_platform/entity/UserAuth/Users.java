package com.running_platform.entity.UserAuth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.running_platform.entity.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Where;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@FieldDefaults(level =  AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "is_deleted=false")
public class Users extends AbstractEntity<Long> {

    @Column(unique = true, nullable = false, columnDefinition = "VARCHAR(50)")
    String username;

    @Column(unique = true, nullable = false, columnDefinition = "VARCHAR(50)")
    String email;

    @JsonProperty(value = "password", access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = true)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    Set<Roles> roles;

    @Column( columnDefinition = "VARCHAR(50)")
    String fullName;


//    @Column(name = "registered_provider_id")
    private String registeredProviderId;

    @Column(nullable = false, columnDefinition = "boolean default false")
    boolean emailVerified = false;


}
