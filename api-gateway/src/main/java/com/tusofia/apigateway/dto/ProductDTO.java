package com.tusofia.apigateway.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
public class ProductDTO {
    private String id;
    private String name;
    private String manufacturer;
    private String description;
    private int quantity;
    private BigDecimal price;
    private String category;
    private List<ReviewDTO> reviews;
    private String imgUrl;
}
