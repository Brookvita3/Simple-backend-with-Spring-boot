package com.simpleprj.project.dto.response;

import com.simpleprj.project.entity.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class PermissionResponse {
     String name;
     String description;
}
