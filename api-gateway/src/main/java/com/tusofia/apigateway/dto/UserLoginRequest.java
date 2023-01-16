package com.tusofia.apigateway.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
@RequiredArgsConstructor
@Getter
@Setter
public class UserLoginRequest {
    @NotEmpty(message = "Username must not be empty!")
    private String username;

    @NotEmpty(message = "Password must not be empty!")
    private String password;
}
