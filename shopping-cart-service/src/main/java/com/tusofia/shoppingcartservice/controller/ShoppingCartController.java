package com.tusofia.shoppingcartservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tusofia.shoppingcartservice.domain.entity.ShoppingCart;
import com.tusofia.shoppingcartservice.domain.entity.ShoppingCartProduct;
import com.tusofia.shoppingcartservice.dto.GetShoppingCartByUsernameDTO;
import com.tusofia.shoppingcartservice.repository.ShoppingCartProductRepository;
import com.tusofia.shoppingcartservice.repository.ShoppingCartRepository;
import com.tusofia.shoppingcartservice.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/internal/shopping-cart/")
public class ShoppingCartController {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartProductRepository shoppingCartProductRepository;
    private final ShoppingCartService shoppingCartService;
    private final ObjectMapper objectMapper;

    @GetMapping(value = "/get-cart-by-username", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String getUserByUsername(@RequestParam String username) throws JsonProcessingException {
        ShoppingCart shoppingCart;

        if (shoppingCartRepository.findByUsername(username).isEmpty()) {
            shoppingCart = new ShoppingCart();
            shoppingCart.setUsername(username);
            shoppingCartRepository.saveAndFlush(shoppingCart);
        }

        shoppingCart = shoppingCartRepository.findByUsername(username).get();

        return objectMapper.writeValueAsString(GetShoppingCartByUsernameDTO.builder()
                .id(shoppingCart.getId())
                .username(shoppingCart.getUsername())
                .productIds(shoppingCart.getProducts().stream().map(ShoppingCartProduct::getProductId).collect(Collectors.toList()))
                .build());
    }

    @GetMapping(value = "/find-all-cart-products", consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<String> findAllShoppingCartProducts(@RequestParam String shoppingCartId) {
        return shoppingCartService.findAllShoppingCartProducts(shoppingCartId).stream()
                .map(product -> {
                    try {
                        return objectMapper.writeValueAsString(product);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toList());
    }

    @GetMapping(value = "/total", consumes = MediaType.APPLICATION_JSON_VALUE)
    public BigDecimal total(@RequestParam String shoppingCartId) {
        return shoppingCartService.total(shoppingCartId);
    }

    @GetMapping(value = "/remove", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void remove(@RequestParam String id) {
        shoppingCartService.remove(id);
    }

    @GetMapping(value = "/removeAll", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String removeAll(@RequestParam String shoppingCartId) throws JsonProcessingException {
        return objectMapper.writeValueAsString(shoppingCartService.removeAll(shoppingCartId));
    }

    @PostMapping(value = "/add-product-to-cart", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addProductToCart(@RequestParam String shoppingCartId,
                       @RequestParam String productId,
                       @RequestParam String name,
                       @RequestParam String description,
                       @RequestParam String manufacturer,
                       @RequestParam String quantity,
                       @RequestParam String productQuantity,
                       @RequestParam String price,
                       @RequestParam String category) {
        shoppingCartService.addProductToCart(shoppingCartId, productId, name, description, manufacturer, quantity, productQuantity, price, category);
    }
}
