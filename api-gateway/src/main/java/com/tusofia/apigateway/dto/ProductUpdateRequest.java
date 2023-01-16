package com.tusofia.apigateway.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Min;

@RequiredArgsConstructor
@Getter
@Setter
public class ProductUpdateRequest {
    @Length(min = 2, message = "Wrong product name!")
    private String name;

    @Min(value = 1, message = "Enter valid quantity!")
    private int quantity;
}
