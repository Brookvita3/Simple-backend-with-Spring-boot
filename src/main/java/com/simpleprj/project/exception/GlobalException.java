package com.simpleprj.project.exception;

import com.simpleprj.project.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.BindException;

@ControllerAdvice
public class GlobalException {
    // Handle for all uncategorized exception in runtime
    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponse<Exception>> handleRuntimeException(RuntimeException e) {
        ApiResponse<Exception> apiResponse = new ApiResponse<>();
        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getErrorCode());
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getErrorMsg());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    // Handle all exception which declare in error code
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse<Exception>> handleAppException(AppException e) {
        ErrorCode errorCode = e.getErrorCode();
        ApiResponse<Exception> apiResponse = new ApiResponse<>();
        apiResponse.setCode(errorCode.getErrorCode());
        apiResponse.setMessage(errorCode.getErrorMsg());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = AuthorizationDeniedException.class )
    ResponseEntity<ApiResponse<Object>> handleAuthenticationException(AuthorizationDeniedException e) {
        ErrorCode errorCode = ErrorCode.UNAUTHENTICATED;
        ApiResponse<Object> apiResponse = new ApiResponse<>();
        apiResponse.setCode(errorCode.getErrorCode());
        apiResponse.setMessage(errorCode.getErrorMsg());
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(ApiResponse.builder()
                        .code(errorCode.getErrorCode())
                        .message(errorCode.getErrorMsg())
                        .build());
    }


    // Handle for invalid error code key
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<BindException>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String enumKey = e.getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.valueOf(enumKey);
        ApiResponse<BindException> apiResponse = new ApiResponse<>();
        apiResponse.setCode(errorCode.getErrorCode());
        apiResponse.setMessage(errorCode.getErrorMsg());
        return ResponseEntity.badRequest().body(apiResponse);
    }

}
