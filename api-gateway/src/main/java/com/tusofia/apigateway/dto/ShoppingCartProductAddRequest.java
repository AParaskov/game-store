package com.tusofia.apigateway.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartProductAddRequest {
    private String productId;
    private int quantity;
}
