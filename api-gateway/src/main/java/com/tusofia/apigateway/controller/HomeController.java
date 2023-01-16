package com.tusofia.apigateway.controller;

import com.tusofia.apigateway.service.ProductServiceConnector;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final ProductServiceConnector productServiceConnector;

    @GetMapping("/")
    public ModelAndView index(HttpSession httpSession, ModelAndView modelAndView){
        if (httpSession.getAttribute("user") == null){
            modelAndView.addObject("products", productServiceConnector.getAllProducts());
            modelAndView.setViewName("index");
        }else{
            modelAndView.addObject("products", productServiceConnector.getAllProducts());
            modelAndView.setViewName("home");
        }

        return modelAndView;
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/home")
    public ModelAndView home(ModelAndView modelAndView) {

        modelAndView.setViewName("/home");
        return modelAndView;
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/home")
    public String homePost() {
        return "redirect:/home";
    }
}
