package ru.lebruce.store.service;

import ru.lebruce.store.model.Review;

import java.util.Date;
import java.util.List;

public interface ReviewService {

    List<Review> findAllReview();

    Review findByReviewDate(Date reviewDate);
    Review findByReviewRating(Double reviewRating);

    Review saveReview(Review review);

    Review updateReview(Review review);

    void deleteReview(Long reviewId);

}
