package com.simpleprj.project.controller;


import com.nimbusds.jose.JOSEException;
import com.simpleprj.project.dto.ApiResponse;
import com.simpleprj.project.dto.request.AuthenticationRequest;
import com.simpleprj.project.dto.request.IntroSpectRequest;
import com.simpleprj.project.dto.request.LogoutRequest;
import com.simpleprj.project.dto.response.AuthenticationResponse;
import com.simpleprj.project.dto.response.IntrospectResponse;
import com.simpleprj.project.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

    AuthenticationService authenticationService;

    @GetMapping("/token")
    public ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.checkPassword(request.getUsername(), request.getPassword());
        return ApiResponse.<AuthenticationResponse>builder()
                .data(result)
                .build();
    }


    @GetMapping("/introspect")
    public ApiResponse<IntrospectResponse> login(@RequestBody IntroSpectRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .data(result)
                .build();
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(@RequestBody LogoutRequest request)
            throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder().build();
    }

}
