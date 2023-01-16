package com.tusofia.apigateway.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@RequiredArgsConstructor
@Getter
@Setter
public class UserRegisterRequest {
    @Length(min = 3, max = 15, message = "Username must be between 3 and 15 characters")
    private String username;

    @Length(min = 3, max = 15, message = "Password must be between 3 and 15 characters")
    private String password;

    private String confirmPassword;

    @Pattern(regexp = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$", message = "Please enter valid email!")
    private String email;
}
