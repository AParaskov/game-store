package com.tusofia.userservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class GetAllUsersResponse {
    List<GetUserDTO> users;
}
