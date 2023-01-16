package com.tusofia.apigateway.controller;

import com.tusofia.apigateway.dto.UserLoginRequest;
import com.tusofia.apigateway.dto.UserRegisterRequest;
import com.tusofia.apigateway.service.UserServiceConnector;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceConnector userServiceConnector;

    @GetMapping("/login")
    public ModelAndView login(@Valid @ModelAttribute("userLoginRequest")
                                  UserLoginRequest userLoginRequest,
                              BindingResult bindingResult, ModelAndView modelAndView){
        modelAndView.addObject("userLoginRequest", userLoginRequest);
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @PostMapping("/login-error")
    public ModelAndView loginError(@ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                                   String username,
                                   ModelAndView modelAndView) {


        modelAndView.addObject("notFound", "bad.credentials");
        modelAndView.addObject("username", username);
        modelAndView.setViewName("login");


        return modelAndView;
    }

    @GetMapping("/register")
    public String register(@Valid @ModelAttribute("userRegisterRequest")
                               UserRegisterRequest userRegisterRequest,
                           BindingResult bindingResult){
        return "register";
    }

    @PostMapping("/register")
    public String registerConfirm(@Valid @ModelAttribute("userRegisterRequest")
                                      UserRegisterRequest userRegisterRequest,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors() || !userRegisterRequest
                .getPassword().equals(userRegisterRequest.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("userRegisterRequest", userRegisterRequest);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterRequest", bindingResult);
            return "redirect:register";
        } else {
            userServiceConnector
                    .registerUser(userRegisterRequest);
            return "redirect:login";
        }

    }
}
