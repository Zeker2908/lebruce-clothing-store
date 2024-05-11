package ru.lebruce.store.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lebruce.store.domain.dto.CreateReviewDTO;
import ru.lebruce.store.domain.exception.ProductNotFoundException;
import ru.lebruce.store.domain.exception.ReviewAlreadyExists;
import ru.lebruce.store.domain.exception.UserNotFoundException;
import ru.lebruce.store.domain.model.Product;
import ru.lebruce.store.domain.model.Review;
import ru.lebruce.store.domain.model.User;
import ru.lebruce.store.repository.ProductRepository;
import ru.lebruce.store.repository.ReviewRepository;
import ru.lebruce.store.repository.UserRepository;
import ru.lebruce.store.service.ReviewService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository repository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

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
    public Review createReview(CreateReviewDTO reviewRequest) {
        if (doesReviewExistForProductAndUser(reviewRequest.getProductId(), reviewRequest.getUserId())) {
            throw new ReviewAlreadyExists("Отзыв пользователя уже существует");
        }

        var review = Review.builder()
                .product(productRepository.findByProductId(reviewRequest.getProductId()).orElseThrow(() -> new ProductNotFoundException("Товар не найден")))
                .user(userRepository.findByUserId(reviewRequest.getUserId()).orElseThrow(() -> new UserNotFoundException("Пользователь не найден")))
                .rating(reviewRequest.getRating())
                .comment(reviewRequest.getComment())
                .build();

        return repository.save(review);

    }


    private boolean doesReviewExistForProductAndUser(Long productId, Long userId) {
        return repository.findByProductIdAndUserId(productId, userId).isPresent();
    }
}
