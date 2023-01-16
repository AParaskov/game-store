package com.tusofia.apigateway.controller;

import com.tusofia.apigateway.dto.RoleAddRequest;
import com.tusofia.apigateway.service.UserServiceConnector;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {
    private final UserServiceConnector userServiceConnector;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/add")
    public ModelAndView add(@Valid @ModelAttribute("roleAddRequest") RoleAddRequest roleAddRequest,
                            ModelAndView modelAndView){

        modelAndView.addObject("role", userServiceConnector.getAllRolesNames());
        modelAndView.addObject("usernames", userServiceConnector.getAllUsersUsernames());
        modelAndView.setViewName("add-role");
        return modelAndView;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public String addConfirm(@Valid @ModelAttribute("roleAddRequest") RoleAddRequest roleAddRequest) {
        userServiceConnector.changeRole(roleAddRequest);

        return "redirect:/home";
    }
}
