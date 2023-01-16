package com.tusofia.shoppingcartservice.service;

import com.tusofia.shoppingcartservice.domain.entity.ShoppingCart;
import com.tusofia.shoppingcartservice.dto.FindAllShoppingCartProductsDTO;
import com.tusofia.shoppingcartservice.dto.UpdateProductQuantityDTO;
import org.hibernate.sql.Update;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

public interface ShoppingCartService {
    void addProductToCart(String shoppingCartId,
                           String productId,
                           String name,
                           String description,
                           String manufacturer,
                           String quantity,
                           String productQuantity,
                           String price,
                           String category);

    List<FindAllShoppingCartProductsDTO> findAllShoppingCartProducts(String shoppingCartId);

    void remove(String id);

    BigDecimal total(String shoppingCartId);

    UpdateProductQuantityDTO removeAll(String id);
}
