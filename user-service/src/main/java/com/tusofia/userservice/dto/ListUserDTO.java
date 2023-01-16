package com.tusofia.userservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ListUserDTO {
    String username;

    String role;

    String status;

    String email;
}
