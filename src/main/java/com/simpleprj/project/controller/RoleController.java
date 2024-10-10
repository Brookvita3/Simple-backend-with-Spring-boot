package com.simpleprj.project.controller;

import com.simpleprj.project.dto.ApiResponse;
import com.simpleprj.project.dto.request.RoleRequest;
import com.simpleprj.project.dto.response.RoleResponse;
import com.simpleprj.project.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleService roleService;

    @PostMapping
    ApiResponse<RoleResponse> createRole(@RequestBody RoleRequest roleRequest) {
        var a = roleService.createRole(roleRequest);
        return ApiResponse.<RoleResponse>builder()
                .data(a)
                .build();
    }

    @GetMapping
    ApiResponse<List<RoleResponse>> getAll() {
        return ApiResponse.<List<RoleResponse>>builder()
                .data(roleService.getAllRole())
                .build();
    }

    @DeleteMapping("/{roleName}")
    ApiResponse<Boolean> deleteRole(@PathVariable String roleName) {
        roleService.deleteRole(roleName);
        return ApiResponse.<Boolean>builder()
                .message(String.format("delete %s successfully", roleName))
                .build();
    }

}
