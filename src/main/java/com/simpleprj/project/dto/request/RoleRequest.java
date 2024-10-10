package com.simpleprj.project.dto.request;
import com.simpleprj.project.entity.Permission;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class RoleRequest {
    @Id
    String name;
    String description;
    Set<String> permissions;
}
