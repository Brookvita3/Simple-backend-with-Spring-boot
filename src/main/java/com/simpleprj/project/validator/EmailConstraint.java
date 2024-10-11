package com.simpleprj.project.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {EmailValidator.class})

public @interface EmailConstraint {
    String message() default "email is invalid";

    int min() default 15;
    int max() default 100;
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
