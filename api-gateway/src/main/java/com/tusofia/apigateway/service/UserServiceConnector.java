package com.tusofia.apigateway.service;

import com.tusofia.apigateway.dto.RoleAddRequest;
import com.tusofia.apigateway.dto.UserDTO;
import com.tusofia.apigateway.dto.UserRegisterRequest;

import java.util.List;

public interface UserServiceConnector {
    UserDTO getUserByUsername(String username);

    List<String> getAllUsersUsernames();

    List<String> getAllRolesNames();

    UserDTO getUserByEmail(String email);

    void registerUser(UserRegisterRequest userRegisterRequest);

    void changeRole(RoleAddRequest roleAddRequest);
}
