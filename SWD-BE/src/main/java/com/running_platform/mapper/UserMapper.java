package com.running_platform.mapper;


import com.running_platform.dto.request.UserRequest;
import com.running_platform.dto.response.UserResponse;
import com.running_platform.entity.UserAuth.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = RoleMapper.class)
public interface UserMapper {

    UserResponse toUserResponse(Users user);
    Users toUser(UserRequest request);
}
