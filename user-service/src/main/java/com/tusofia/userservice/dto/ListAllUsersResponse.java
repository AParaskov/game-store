package com.tusofia.userservice.dto;

import com.tusofia.userservice.domain.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@Builder
public class ListAllUsersResponse {
    List<ListUserDTO> users;

    int totalCount;
}
