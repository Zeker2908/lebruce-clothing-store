package ru.lebruce.store.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lebruce.store.domain.dto.ReviewRequest;
import ru.lebruce.store.domain.model.Review;
import ru.lebruce.store.exception.ReviewAlreadyExists;
import ru.lebruce.store.exception.UserNotFoundException;
import ru.lebruce.store.repository.ReviewRepository;
import ru.lebruce.store.repository.UserRepository;
import ru.lebruce.store.service.ProductService;
import ru.lebruce.store.service.ReviewService;

import java.util.List;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository repository;
    private final UserRepository userRepository;
    private final ProductService productService;

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
    public void createReview(ReviewRequest reviewRequest) {
        if (doesReviewExistForProductAndUser(reviewRequest.getProductId(), reviewRequest.getUserId())) {
            throw new ReviewAlreadyExists("Отзыв пользователя уже существует");
        }

        var review = Review.builder()
                .product(productService.getByProductId(reviewRequest.getProductId()))
                .user(userRepository.findByUserId(reviewRequest.getUserId()).orElseThrow(() -> new UserNotFoundException("Пользователь не найден")))
                .rating(reviewRequest.getRating())
                .comment(reviewRequest.getComment())
                .build();
        repository.save(review);
    }

    private boolean doesReviewExistForProductAndUser(Long productId, Long userId) {
        return repository.findByProductIdAndUserId(productId, userId).isPresent();
    }
}
