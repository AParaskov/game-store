package com.tusofia.productservice.service;

import com.tusofia.productservice.dto.AddReviewRequest;

public interface ReviewService {
    void addReview(String username, AddReviewRequest addReviewRequest);

    void cleanOldReviews();
}
