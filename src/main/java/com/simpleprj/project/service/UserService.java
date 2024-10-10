package com.simpleprj.project.service;

import com.simpleprj.project.dto.request.UserCreationRequest;
import com.simpleprj.project.dto.request.UserUpdateRequest;
import com.simpleprj.project.dto.response.UserResponse;
import com.simpleprj.project.entity.User;
import com.simpleprj.project.enums.Roles;
import com.simpleprj.project.exception.AppException;
import com.simpleprj.project.exception.ErrorCode;
import com.simpleprj.project.mapper.UserMapper;
import com.simpleprj.project.repository.RoleRepository;
import com.simpleprj.project.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)


public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    public UserResponse createUser(UserCreationRequest request){
        if (userRepository.existsByUsername(request.getUsername())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        User user = userMapper.toUser(request);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Roles.USER.name());
        //user.setRoles(roles);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    //@PreAuthorize("hasRole('adminRole')")
    @PreAuthorize("hasAuthority('p1')")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }


    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUserById(String Id){
        return userMapper.toUserResponse(userRepository
                .findById(Id)
                .orElseThrow(() -> new RuntimeException("User not found")));
    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }

    public UserResponse updateUser(String userId, UserUpdateRequest request){
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        userMapper.updateUser(user, request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}
