package com.tusofia.userservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegisterUserRequest {
    private String username;

    private String password;

    private String confirmPassword;

    private String email;
}
