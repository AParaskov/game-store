package com.tusofia.userservice.repository;

import com.tusofia.userservice.domain.entity.Role;
import com.tusofia.userservice.domain.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Role findByName(RoleName name);
}
