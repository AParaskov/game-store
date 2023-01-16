package com.tusofia.apigateway.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@RequiredArgsConstructor
@Getter
@Setter
public class ProductAddRequest {
    @Length(min = 2, message = "Name length must be more than two characters")
    private String name;

    @Length(min = 3, message = "Manufacturer name must be more than 3 characters")
    private String manufacturer;

    @Length(min = 2, message = "Description length must be more than two characters")
    private String description;

    @Min(value = 1, message = "Enter valid quantity!")
    private int quantity;

    @DecimalMin(value = "0", message = "Enter valid price!")
    private BigDecimal price;
}
