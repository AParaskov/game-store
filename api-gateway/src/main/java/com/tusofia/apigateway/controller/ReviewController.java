package com.tusofia.apigateway.controller;

import com.tusofia.apigateway.dto.ReviewAddRequest;
import com.tusofia.apigateway.service.ProductServiceConnector;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ProductServiceConnector productServiceConnector;

    @GetMapping("/add")
    public ModelAndView add(@Valid @ModelAttribute("reviewAddRequest") ReviewAddRequest reviewAddRequest,
                            BindingResult bindingResult, ModelAndView modelAndView, HttpServletRequest httpServletRequest,
                            @RequestParam("product_id") String productId){
        reviewAddRequest.setProductId(productId);
        Principal user = httpServletRequest.getUserPrincipal();
        modelAndView.addObject("reviewAddRequest", reviewAddRequest);
        modelAndView.addObject("username", user.getName());
        modelAndView.setViewName("add-review");
        return modelAndView;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/add")
    public String addConfirm(@Valid @ModelAttribute("reviewAddRequest") ReviewAddRequest reviewAddRequest,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("reviewAddRequest", reviewAddRequest);
            return "redirect:/reviews/add";
        } else {
            Principal user = httpServletRequest.getUserPrincipal();
            productServiceConnector
                    .addReview(reviewAddRequest, user.getName());
            return "redirect:/home";
        }
    }
}
