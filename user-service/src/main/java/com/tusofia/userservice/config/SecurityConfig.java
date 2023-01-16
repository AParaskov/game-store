package com.tusofia.userservice.config;

import com.tusofia.userservice.service.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
public class SecurityConfig {
    //private final UserDetailsServiceImpl userDetailsService;
//
    //private final BCryptPasswordEncoder passwordEncoder;
//
    //@Bean
    //public AuthenticationManager authenticationManager(HttpSecurity http)
    //        throws Exception {
    //    return http.getSharedObject(AuthenticationManagerBuilder.class)
    //            .userDetailsService(userDetailsService)
    //            .passwordEncoder(passwordEncoder)
    //            .and()
    //            .build();
    //}
//
    //@Bean
    //public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    //    http.cors().and().csrf().disable()
    //            .authorizeRequests()
    //            .requestMatchers("/users/register", "/users/internal/get-all-users").permitAll()
    //            .requestMatchers("/users/deactivate", "/users/activate", "/users/change-role-to-user", "/users/change-role-to-admin", "/users/list").hasRole("ADMIN")
    //            .requestMatchers("/users/edit").authenticated()
    //            .anyRequest().denyAll()
    //            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    //            .and().httpBasic();
//
    //    return http.build();
    //}
}
