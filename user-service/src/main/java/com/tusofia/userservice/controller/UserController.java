package com.tusofia.userservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tusofia.userservice.domain.entity.Role;
import com.tusofia.userservice.domain.entity.User;
import com.tusofia.userservice.dto.*;
import com.tusofia.userservice.repository.RoleRepository;
import com.tusofia.userservice.repository.UserRepository;
import com.tusofia.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/internal/users")
public class UserController {
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void registerUser(@RequestBody Object registerUserRequest) {
        Map<String, String> requestBody = (Map<String, String>) registerUserRequest;
        RegisterUserRequest request = RegisterUserRequest.builder()
                        .username(requestBody.get("username"))
                        .password(requestBody.get("password"))
                        .confirmPassword(requestBody.get("confirmPassword"))
                        .email(requestBody.get("email"))
                        .build();
        userService.registerUser(request);
    }

    @PutMapping(value = "/change-role", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void changeRole(@RequestParam String username, @RequestParam String role) {
        userService.changeUserRole(username, role);
    }

    @GetMapping(value = "/get-user-by-username", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String getUserByUsername(@RequestParam String username) throws JsonProcessingException {
        User user = userRepository.findByUsername(username).get();

        return objectMapper.writeValueAsString(GetUserByUsernameDTO.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole().getName().name())
                .build());
    }

    @GetMapping(value = "/get-user-by-email", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object getUserByEmail(@RequestParam String email) {
        User user = userRepository.findByEmail(email).get();

        return GetUserByUsernameDTO.builder()
                .username(user.getUsername())
                .password(bCryptPasswordEncoder.encode(user.getPassword()))
                .role(user.getRole().getName().name());
    }

    @GetMapping(value = "/get-all-users-usernames", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String getAllUsersUsernames() {
        return userRepository.findAll()
                .stream()
                .map(User::getUsername)
                .collect(Collectors.joining(","));
    }

    @GetMapping(value = "/get-all-roles-names", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String getAllRolesNames() {
        return roleRepository.findAll()
                .stream()
                .map(role -> role.getName().name())
                .collect(Collectors.joining(","));
    }
}
