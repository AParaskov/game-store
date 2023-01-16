package com.tusofia.userservice.service.impl;

import com.tusofia.userservice.domain.entity.User;
import com.tusofia.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userToLoad = this.userRepository.findByUsername(username).get();
        return org.springframework.security.core.userdetails.User.builder()
                .username(userToLoad.getUsername())
                .password(userToLoad.getPassword())
                .roles(userToLoad.getRole().getName().name())
                .build();
    }
}
