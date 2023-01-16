package com.tusofia.userservice.service.impl;

import com.tusofia.userservice.domain.entity.User;
import com.tusofia.userservice.domain.enums.RoleName;
import com.tusofia.userservice.dto.*;
import com.tusofia.userservice.repository.UserRepository;
import com.tusofia.userservice.service.RoleService;
import com.tusofia.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class  UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void registerUser(RegisterUserRequest registerUserRequest) {
        User user = User.builder()
                .username(registerUserRequest.getUsername())
                .password(passwordEncoder.encode(registerUserRequest.getPassword()))
                .role(roleService.findByName(userRepository.count() == 0 ? RoleName.ADMIN : RoleName.USER))
                .email(registerUserRequest.getEmail())
                .build();

        userRepository.saveAndFlush(user);
    }

    @Override
    public void changeUserRole(String username, String role) {
        User user = this.userRepository
                .findByUsername(username)
                .orElse(null);


        if (!user.getRole().getName().name().equals(role)) {
            if(role.equals("ADMIN")) {
                user.setRole(roleService.findByName(RoleName.ADMIN));
            } else {
                user.setRole(roleService.findByName(RoleName.USER));
            }
            this.userRepository.saveAndFlush(user);
        }
    }
}
