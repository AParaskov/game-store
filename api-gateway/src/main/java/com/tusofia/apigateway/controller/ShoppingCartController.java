package com.tusofia.apigateway.controller;

import com.tusofia.apigateway.dto.ProductDTO;
import com.tusofia.apigateway.dto.ShoppingCartDTO;
import com.tusofia.apigateway.dto.ShoppingCartProductAddRequest;
import com.tusofia.apigateway.dto.ShoppingCartProductDTO;
import com.tusofia.apigateway.service.ProductServiceConnector;
import com.tusofia.apigateway.service.ShoppingCartServiceConnector;
import com.tusofia.apigateway.service.impl.ProductServiceConnectorImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/shopping-cart")
@RequiredArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartServiceConnector shoppingCartServiceConnector;

    private final ProductServiceConnector productServiceConnector;

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ModelAndView shopping_cart(ModelAndView modelAndView, HttpServletRequest httpServletRequest) {
        Principal principal = httpServletRequest.getUserPrincipal();
        ShoppingCartDTO shoppingCart = shoppingCartServiceConnector.findCartByUsername(principal.getName());
        modelAndView.addObject("shoppingCartId", shoppingCart.getId());
        modelAndView.addObject("shoppingCartProducts", shoppingCartServiceConnector.findAllShoppingCartProducts(shoppingCart.getId()));
        modelAndView.addObject("total", shoppingCartServiceConnector.total(shoppingCart.getId()));
        modelAndView.setViewName("shopping-cart");
        return modelAndView;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("shoppingCartProductAddRequest") ShoppingCartProductAddRequest shoppingCartProductAddRequest,
                      BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("shoppingCartProductAddRequest", shoppingCartProductAddRequest);
            return "redirect:/details";
        }

        Principal principal = httpServletRequest.getUserPrincipal();
        ShoppingCartDTO shoppingCart = shoppingCartServiceConnector.findCartByUsername(principal.getName());

        ProductDTO product = productServiceConnector.getProductById(shoppingCartProductAddRequest.getProductId());

        ShoppingCartProductDTO shoppingCartProductDTO = ShoppingCartProductDTO.builder()
                .shoppingCartId(shoppingCart.getId())
                .productId(shoppingCartProductAddRequest.getProductId())
                .quantity(shoppingCartProductAddRequest.getQuantity())
                .name(product.getName())
                .manufacturer(product.getManufacturer())
                .category(product.getCategory())
                .description(product.getDescription())
                .price(product.getPrice())
                .productQuantity(product.getQuantity())
                .build();


        shoppingCartServiceConnector.addProductToCart(shoppingCartProductDTO);
        return "redirect:/home";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/remove/{id}")
    public String delete(@PathVariable("id") String shoppingCartProductId) {
        shoppingCartServiceConnector.remove(shoppingCartProductId);
        return "redirect:/shopping-cart";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/remove-all/{id}")
    public String deleteAll(@PathVariable("id") String id) {
        shoppingCartServiceConnector.removeAll(id);
        return "redirect:/home";
    }
}
