package com.simpleprj.project.controller;

import com.simpleprj.project.dto.ApiResponse;
import com.simpleprj.project.dto.request.PermissionRequest;
import com.simpleprj.project.dto.response.PermissionResponse;
import com.simpleprj.project.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
    PermissionService permissionService;

    @PostMapping
    ApiResponse<PermissionResponse> createPermission(@RequestBody PermissionRequest permissionRequest) {
        return ApiResponse.<PermissionResponse>builder()
                .data(permissionService.createPermission(permissionRequest))
                .build();
    }

    @GetMapping
    ApiResponse<List<PermissionResponse>> getAll() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .data(permissionService.getAllPermissions())
                .build();
    }

    @DeleteMapping("/{permissionName}")
    ApiResponse<Boolean> deletePermission(@PathVariable String permissionName) {
        permissionService.deletePermission(permissionName);
        return ApiResponse.<Boolean>builder()
                .message(String.format("delete %s successfully", permissionName))
                .build();
    }

}
