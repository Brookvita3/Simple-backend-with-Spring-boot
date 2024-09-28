package com.simpleprj.project.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum ErrorCode {

    INVALID_KEY(1001, "Categorized error", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User already exists", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1003, "User not exists", HttpStatus.NOT_FOUND),
    USERNAME_INVALID(1004, "username is invalid", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1005, "you are not authenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1006, "You do not have permission", HttpStatus.FORBIDDEN),
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR),;

    private final int ErrorCode;
    private final String ErrorMsg;
    private final HttpStatus httpStatus;

}
