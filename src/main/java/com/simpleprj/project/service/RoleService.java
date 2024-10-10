package com.simpleprj.project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simpleprj.project.dto.request.PermissionRequest;
import com.simpleprj.project.dto.request.RoleRequest;
import com.simpleprj.project.dto.response.PermissionResponse;
import com.simpleprj.project.dto.response.RoleResponse;
import com.simpleprj.project.entity.Permission;
import com.simpleprj.project.entity.Role;
import com.simpleprj.project.mapper.PermissionMapper;
import com.simpleprj.project.mapper.RoleMapper;
import com.simpleprj.project.repository.PermissionRepository;
import com.simpleprj.project.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    public RoleResponse createRole(RoleRequest roleRequest) {
        var role = roleMapper.toRole(roleRequest);
        var permissions =  permissionRepository.findAllById(roleRequest.getPermissions());
        role.setPermissions(new HashSet<>(permissions));
        return roleMapper.toRoleResponse(roleRepository.save(role));
    }

    public List<RoleResponse> getAllRole() {
        return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
    }

    public void deleteRole(String roleName) {
        roleRepository.deleteById(roleName);
    }

}
