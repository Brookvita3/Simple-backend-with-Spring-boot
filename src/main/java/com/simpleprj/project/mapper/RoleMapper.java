package com.simpleprj.project.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.simpleprj.project.dto.request.RoleRequest;
import com.simpleprj.project.dto.response.RoleResponse;
import com.simpleprj.project.entity.Role;


@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);

}
