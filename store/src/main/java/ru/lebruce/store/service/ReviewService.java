package ru.lebruce.store.service;

import ru.lebruce.store.domain.dto.ReviewRequest;
import ru.lebruce.store.domain.model.Review;

import java.util.List;

public interface ReviewService {
    List<Review> findAll();

    Review saveReview(Review review);

    Review updateReview(Review review);

    void deleteReview(Long reviewId);

    void createReview(ReviewRequest reviewRequest);
}
