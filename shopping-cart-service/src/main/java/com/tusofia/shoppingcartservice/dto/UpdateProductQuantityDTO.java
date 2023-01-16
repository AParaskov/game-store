package com.tusofia.shoppingcartservice.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
public class UpdateProductQuantityDTO {
    List<String> productIds;
    List<String> productQuantities;

    public UpdateProductQuantityDTO() {
        this.productIds = new ArrayList<>();
        this.productQuantities = new ArrayList<>();
    }
}
