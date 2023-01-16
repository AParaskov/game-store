package com.tusofia.apigateway.service;

import com.tusofia.apigateway.dto.ReviewAddRequest;
import com.tusofia.apigateway.dto.ShoppingCartDTO;
import com.tusofia.apigateway.dto.ShoppingCartProductDTO;
import com.tusofia.apigateway.dto.ShoppingCartProductViewDTO;

import java.math.BigDecimal;
import java.util.List;

public interface ShoppingCartServiceConnector {
    ShoppingCartDTO findCartByUsername(String username);

    List<ShoppingCartProductViewDTO> findAllShoppingCartProducts(String shoppingCartId);

    BigDecimal total(String shoppingCartId);

    void remove(String shoppingCartProductId);

    void removeAll(String shoppingCartId);

    void addProductToCart(ShoppingCartProductDTO shoppingCartProductDTO);

}
