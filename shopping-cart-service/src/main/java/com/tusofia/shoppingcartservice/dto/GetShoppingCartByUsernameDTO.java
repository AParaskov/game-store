package com.tusofia.shoppingcartservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class GetShoppingCartByUsernameDTO {
    String id;
    String username;
    List<String> productIds;
}
