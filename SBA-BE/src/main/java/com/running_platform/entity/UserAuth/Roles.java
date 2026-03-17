package com.running_platform.entity.UserAuth;

import com.running_platform.enums.RoleEnum;
import com.running_platform.entity.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Where;

import java.util.List;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "is_deleted=false")
public class Roles extends AbstractEntity<Long>{
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    RoleEnum roleName;

    @ManyToMany(mappedBy = "roles")
    List<Users> users;

}
