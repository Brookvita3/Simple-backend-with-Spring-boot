package com.simpleprj.project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simpleprj.project.dto.request.UserCreationRequest;
import com.simpleprj.project.dto.response.UserResponse;
import com.simpleprj.project.entity.User;
import com.simpleprj.project.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    private UserRepository userRepository;

    private UserCreationRequest userCreationRequest;
    private UserResponse userResponse;
    private User user;

    @Autowired
    private UserService userService;

    @BeforeEach
    void init() {
        userCreationRequest = UserCreationRequest.builder()
                .username("hahaha")
                .email("hahaha123@gmail.com")
                .phoneNumber("1234567890")
                .password("1234567890")
                .build();
        userResponse = UserResponse.builder()
                .username("hahaha")
                .email("hahaha123@gmail.com")
                .phoneNumber("1234567890")
                .build();
        user = User.builder()
                .id("9876")
                .username("hahaha")
                .email("hahaha123@gmail.com")
                .phoneNumber("1234567890")
                .password("1234567890")
                .build();
    }

    @Test
    void createUser_Validrequest_success() throws Exception {
        //GIVEN
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);

        //WHEN
        var response = userService.createUser(userCreationRequest);

        //THEN
        Assertions.assertThat(response.getId()).isEqualTo("9876");
        Assertions.assertThat(response.getUsername()).isEqualTo("hahaha");
    }


}
