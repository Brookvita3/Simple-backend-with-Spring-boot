package com.simpleprj.project.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.simpleprj.project.dto.request.UserCreationRequest;
import com.simpleprj.project.dto.response.UserResponse;
import com.simpleprj.project.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/test.properties")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;


    private UserCreationRequest userCreationRequest;
    private UserResponse userResponse;

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
    }



    @Test
    void createUser_Validrequest_success() throws Exception {
        //GIVEN
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(userCreationRequest);

        Mockito.when(userService.createUser(ArgumentMatchers.any())).thenReturn(userResponse);

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1000));

        //THEN
    }
}
