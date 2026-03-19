package com.running_platform.mapper;

import com.running_platform.dto.request.UserRequest;
import com.running_platform.dto.response.UserResponse;
import com.running_platform.entity.UserAuth.Roles;
import com.running_platform.entity.UserAuth.Users;
import com.running_platform.enums.RoleEnum;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-18T12:19:52+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.15 (Microsoft)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public UserResponse toUserResponse(Users user) {
        if ( user == null ) {
            return null;
        }

        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();

        userResponse.id( user.getId() );
        userResponse.username( user.getUsername() );
        userResponse.emailVerified( user.isEmailVerified() );
        userResponse.roles( roleMapper.toResponse( user.getRoles() ) );
        userResponse.email( user.getEmail() );
        userResponse.fullName( user.getFullName() );

        return userResponse.build();
    }

    @Override
    public Users toUser(UserRequest request) {
        if ( request == null ) {
            return null;
        }

        Users.UsersBuilder users = Users.builder();

        users.username( request.getUsername() );
        users.email( request.getEmail() );
        users.password( request.getPassword() );
        users.roles( roleEnumSetToRolesSet( request.getRoles() ) );
        users.fullName( request.getFullName() );
        users.emailVerified( request.isEmailVerified() );

        return users.build();
    }

    protected Roles roleEnumToRoles(RoleEnum roleEnum) {
        if ( roleEnum == null ) {
            return null;
        }

        Roles.RolesBuilder roles = Roles.builder();

        return roles.build();
    }

    protected Set<Roles> roleEnumSetToRolesSet(Set<RoleEnum> set) {
        if ( set == null ) {
            return null;
        }

        Set<Roles> set1 = new LinkedHashSet<Roles>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( RoleEnum roleEnum : set ) {
            set1.add( roleEnumToRoles( roleEnum ) );
        }

        return set1;
    }
}
