package com.tusofia.apigateway.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class RoleAddRequest {
    private String username;
    private String role;
}
