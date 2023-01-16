package com.tusofia.productservice.service.impl;

import com.tusofia.productservice.domain.entity.Product;
import com.tusofia.productservice.domain.entity.Review;
import com.tusofia.productservice.dto.AddReviewRequest;
import com.tusofia.productservice.repository.ProductRepository;
import com.tusofia.productservice.repository.ReviewRepository;
import com.tusofia.productservice.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ProductRepository productRepository;

    private final ReviewRepository reviewRepository;

    @Override
    public void addReview(String username, AddReviewRequest addReviewRequest) {
        Product product = productRepository
                .findById(addReviewRequest.getProductId())
                .orElseThrow();
        Review review = Review.builder()
                .text(addReviewRequest.getText())
                .addedOn(LocalDateTime.now())
                .user(username)
                .product(product)
                .build();

        reviewRepository.saveAndFlush(review);
    }

    @Override
    public void cleanOldReviews() {
        LocalDateTime endTime = LocalDateTime.now().minus(20, ChronoUnit.DAYS);
        reviewRepository.deleteByAddedOnBefore(endTime);
    }
}
