package com.tusofia.apigateway.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ShoppingCartProductDTO {
    private String productId;
    private String name;
    private String manufacturer;
    private String description;
    private int productQuantity;
    private int quantity;
    private BigDecimal price;
    private String category;
    private String shoppingCartId;
}
