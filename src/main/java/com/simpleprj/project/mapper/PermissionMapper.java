package com.simpleprj.project.mapper;

import com.simpleprj.project.dto.request.PermissionRequest;
import com.simpleprj.project.dto.response.PermissionResponse;
import com.simpleprj.project.entity.Permission;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);

}
