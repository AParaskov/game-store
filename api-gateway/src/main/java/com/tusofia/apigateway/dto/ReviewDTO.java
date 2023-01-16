package com.tusofia.apigateway.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
@Setter
public class ReviewDTO {
    private String userName;
    private String text;
    private String addedOn;
    private String productId;
}
