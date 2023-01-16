package com.tusofia.userservice.service;

import com.tusofia.userservice.domain.entity.Role;
import com.tusofia.userservice.domain.enums.RoleName;

public interface RoleService {
    Role findByName(RoleName name);
}
