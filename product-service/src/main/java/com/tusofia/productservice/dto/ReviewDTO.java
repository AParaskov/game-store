package com.tusofia.productservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ReviewDTO {
    private String userName;
    private String text;
    private String addedOn;
    private String productId;
}
