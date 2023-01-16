package com.tusofia.userservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChangeUserStatusOrRoleResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String username;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String role;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String status;

    String message;
}
