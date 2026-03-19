package com.running_platform.mapper;

import com.running_platform.dto.response.RoleResponse;
import com.running_platform.entity.UserAuth.Roles;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-18T11:58:29+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.15 (Microsoft)"
)
@Component
public class RoleMapperImpl implements RoleMapper {

    @Override
    public Set<RoleResponse> toResponse(Set<Roles> roles) {
        if ( roles == null ) {
            return null;
        }

        Set<RoleResponse> set = new LinkedHashSet<RoleResponse>( Math.max( (int) ( roles.size() / .75f ) + 1, 16 ) );
        for ( Roles roles1 : roles ) {
            set.add( rolesToRoleResponse( roles1 ) );
        }

        return set;
    }

    protected RoleResponse rolesToRoleResponse(Roles roles) {
        if ( roles == null ) {
            return null;
        }

        RoleResponse.RoleResponseBuilder roleResponse = RoleResponse.builder();

        roleResponse.roleName( roles.getRoleName() );

        return roleResponse.build();
    }
}
