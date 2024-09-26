package com.simpleprj.project.mapper;

import com.simpleprj.project.dto.request.UserCreationRequest;
import com.simpleprj.project.dto.request.UserUpdateRequest;
import com.simpleprj.project.dto.response.UserResponse;
import com.simpleprj.project.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
    void updateUser(@MappingTarget User user, UserUpdateRequest request);

    //@Mapping(ignore = true)
    UserResponse toUserResponse(User user);
}
