package com.simpleprj.project.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;




@Getter
@AllArgsConstructor
public enum ErrorCode {

    USER_EXISTED(1001, "User already exists"),
    USER_NOT_EXISTED(1002, "User not exists"),
    USERNAME_INVALID(1003, "username is invalid"),
    UNAUTHORIZED(1004, "Unauthorized"),
    UNCATEGORIZED_EXCEPTION(9999, "uncategorized message"),;

    private final int ErrorCode;
    private final String ErrorMsg;

}
