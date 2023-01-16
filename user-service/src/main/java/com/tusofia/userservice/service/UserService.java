package com.tusofia.userservice.service;

import com.tusofia.userservice.dto.*;

public interface UserService {
    void registerUser(RegisterUserRequest registerUserRequest);

    void changeUserRole(String username, String role);
}
