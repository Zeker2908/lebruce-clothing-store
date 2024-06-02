package ru.lebruce.store.service;

import org.springframework.data.domain.Page;
import ru.lebruce.store.domain.dto.ReviewRequest;
import ru.lebruce.store.domain.model.Review;

public interface ReviewService {
    Page<Review> findAllByProductId(Long productId, int page, int size, String[] sort);

    Review findByProductIdAndCurrentUser(Long productId);

    Review updateReview(Review review);

    void deleteReview(Long reviewId);

    void createReview(ReviewRequest reviewRequest);
}
