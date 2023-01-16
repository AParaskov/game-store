package com.tusofia.userservice.service.impl;

import com.tusofia.userservice.domain.entity.Role;
import com.tusofia.userservice.domain.enums.RoleName;
import com.tusofia.userservice.repository.RoleRepository;
import com.tusofia.userservice.service.RoleService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @PostConstruct
    public void init(){
        if (roleRepository.count() == 0){
            Role roleAdmin = Role.builder()
                    .name(RoleName.ADMIN)
                    .build();

            Role roleUser = Role.builder()
                    .name(RoleName.USER)
                    .build();

            roleRepository.save(roleAdmin);
            roleRepository.save(roleUser);
        }
    }

    @Override
    public Role findByName(RoleName name) {
        return roleRepository.findByName(name);
    }
}
