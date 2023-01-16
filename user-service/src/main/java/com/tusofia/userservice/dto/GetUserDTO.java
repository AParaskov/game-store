package com.tusofia.userservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetUserDTO {
    String id;

    String username;

    String password;

    String role;

    String status;

    String email;
}
