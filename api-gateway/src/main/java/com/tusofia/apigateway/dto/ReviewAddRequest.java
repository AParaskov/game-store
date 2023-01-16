package com.tusofia.apigateway.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
@Setter
public class ReviewAddRequest {
    @Length(min = 2, message = "Review must be more than two characters!")
    private String text;

    private String productId;
}
