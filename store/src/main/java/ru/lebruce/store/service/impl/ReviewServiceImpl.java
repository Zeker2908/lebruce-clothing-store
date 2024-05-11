package ru.lebruce.store.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lebruce.store.domain.exception.ReviewAlreadyExists;
import ru.lebruce.store.domain.model.Review;
import ru.lebruce.store.repository.ReviewRepository;
import ru.lebruce.store.service.ReviewService;

import java.util.List;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository repository;

    @Override
    public List<Review> findAll() {
        return repository.findAll();
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
        repository.deleteByReviewId(reviewId);
    }

    @Override
    public Review createReview(Review review) {
        if(doesReviewExistForProductAndUser(review.getProduct().getProductId(), review.getUser().getUserId())) {
            throw new ReviewAlreadyExists("Отзыв пользователя уже существует");
        }
        return saveReview(review);
    }

    @Override
    public boolean doesReviewExistForProductAndUser(Long productId, Long userId) {
        return repository.findByProductIdAndUserId(productId, userId).isPresent();
    }
}
