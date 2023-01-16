package com.tusofia.apigateway.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductQuantityDTO {
    List<String> productIds;
    List<String> productQuantities;
}
