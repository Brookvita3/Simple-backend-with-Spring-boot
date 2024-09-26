package com.simpleprj.project.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class UserUpdateRequest {
    // example for create error code for validation (maybe not good, but I don't know how to handle it)
    @NotNull(message = "username cannot be null")
    @Size(min = 5, message = "USERNAME_INVALID")
    String username;
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotNull(message = "email cannot be null")
    String email;
    @NotNull(message = "phoneNumer cannot be null")
    @Size(min = 10, max = 10, message = "invalid phone number")
    @Pattern(regexp = "^[0-9]{10}$", message = "invalid phone number")
    String phoneNumber;
    @Size(min = 6, max = 16, message = "at least 6 characters")
    @NotNull(message = "password cannot be null")
    String password;
}
