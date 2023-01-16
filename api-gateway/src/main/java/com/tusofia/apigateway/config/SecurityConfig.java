package com.tusofia.apigateway.config;

import com.tusofia.apigateway.service.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
   private final UserDetailsServiceImpl userDetailsService;
   private final BCryptPasswordEncoder passwordEncoder;
   @Bean
   public AuthenticationManager authenticationManager(HttpSecurity http)
           throws Exception {
       return http.getSharedObject(AuthenticationManagerBuilder.class)
               .userDetailsService(userDetailsService)
               .passwordEncoder(passwordEncoder)
               .and()
               .build();
   }
   @Bean
   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       http.authorizeRequests()
               .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
               .requestMatchers("/img/**", "/css/**", "/users/login/**", "/users/login-error/**", "/users/register", "/about", "/").permitAll()
               .anyRequest().authenticated()
               .and()
               .formLogin()
               .loginPage("/users/login")
               .loginProcessingUrl("/login/authenticate")
               .failureForwardUrl("/users/login-error")
               .successForwardUrl("/home")
               .permitAll()
               .and()
               .logout()
               .logoutUrl("/logout")
               .logoutSuccessUrl("/")
               .invalidateHttpSession(true)
               .deleteCookies("JSESSIONID")
               .permitAll();

       return http.build();
   }
}
