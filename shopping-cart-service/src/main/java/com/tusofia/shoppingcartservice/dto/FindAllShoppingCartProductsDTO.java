package com.tusofia.shoppingcartservice.dto;

import com.tusofia.shoppingcartservice.domain.entity.ShoppingCart;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
public class FindAllShoppingCartProductsDTO {
    private String id;
    private String name;
    private String manufacturer;
    private String description;
    private int quantity;
    private BigDecimal price;
    private String imgUrl;

}
