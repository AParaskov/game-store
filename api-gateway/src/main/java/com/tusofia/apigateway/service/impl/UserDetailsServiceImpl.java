package com.tusofia.apigateway.service.impl;

import com.tusofia.apigateway.dto.UserDTO;
import com.tusofia.apigateway.service.UserServiceConnector;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserServiceConnector userServiceConnector;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO userToLoad = this.userServiceConnector.getUserByUsername(username);
        return org.springframework.security.core.userdetails.User.builder()
                .username(userToLoad.getUsername())
                .password(userToLoad.getPassword())
                .roles(userToLoad.getRole())
                .build();
    }
}
