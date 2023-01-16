package com.tusofia.productservice.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.validator.constraints.Length;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AddReviewRequest {
    private String text;
    private String productId;
}
