package com.tusofia.apigateway.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartDTO {
    String id;
    String username;
    List<String> productIds;
}
