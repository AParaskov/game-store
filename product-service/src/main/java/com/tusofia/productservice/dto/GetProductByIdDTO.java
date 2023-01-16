package com.tusofia.productservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
public class GetProductByIdDTO {
    private String id;
    private String name;
    private String manufacturer;
    private String description;
    private int quantity;
    private String category;
    private List<ReviewDTO> reviews;
    private BigDecimal price;
    private String imgUrl;
}
