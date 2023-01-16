package com.tusofia.productservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class AddProductRequest {
    private String name;

    private String manufacturer;

    private String description;

    private int quantity;

    private BigDecimal price;

    private String category;
}
