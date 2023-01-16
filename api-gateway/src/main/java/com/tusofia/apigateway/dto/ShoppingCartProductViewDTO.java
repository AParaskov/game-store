package com.tusofia.apigateway.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartProductViewDTO {
    private String id;
    private String name;
    private String manufacturer;
    private String description;
    private int quantity;
    private BigDecimal price;
    private String imgUrl;
}
