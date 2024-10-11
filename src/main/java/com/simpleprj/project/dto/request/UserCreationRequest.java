package com.simpleprj.project.dto.request;
import com.simpleprj.project.validator.EmailConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class UserCreationRequest {
    // example for create error code for validation (maybe not good, but I don't know how to handle it)
    @NotNull(message = "username cannot be null")
    @Size(min = 5, message = "USERNAME_INVALID")
    String username;
    @Email(message = "Email is not valid")
    @EmailConstraint( max = 100, message = "INVALID_EMAIL")
    @NotNull(message = "Email cannot be null")
    String email;
    @NotNull(message = "phoneNumer cannot be null")
    @Size(min = 10, max = 10, message = "invalid phone number")
    @Pattern(regexp = "^[0-9]{10}$", message = "invalid phone number")
    String phoneNumber;
    @Size(min = 6, max = 16, message = "at least 6 characters")
    @NotNull(message = "password cannot be null")
    String password;
}
