package ru.lebruce.store.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lebruce.store.model.Review;
import ru.lebruce.store.repository.ReviewsRepository;
import ru.lebruce.store.service.ReviewService;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ReviewDetailServiceImpl implements ReviewService {

    private final ReviewsRepository repository;

    @Override
    public List<Review> findAllReview() {
        return repository.findAll();
    }

    @Override
    public Review findByReviewDate(Date reviewDate) {
        return repository.findByReviewDate(reviewDate);
    }

    @Override
    public Review findByReviewRating(Double reviewRating) {
        return repository.findByReviewRating(reviewRating);
    }

    @Override
    public Review saveReview(Review review) {
        return repository.save(review);
    }

    @Override
    public Review updateReview(Review review) {
        return repository.save(review);
    }

    @Override
    @Transactional
    public void deleteReview(Long reviewId) {
            repository.deleteReview(reviewId);
    }


}
