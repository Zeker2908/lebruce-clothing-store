package ru.lebruce.store.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.lebruce.store.domain.dto.ReviewRequest;
import ru.lebruce.store.domain.model.Product;
import ru.lebruce.store.domain.model.Review;
import ru.lebruce.store.exception.ReviewAlreadyExists;
import ru.lebruce.store.exception.ReviewNotFoundException;
import ru.lebruce.store.repository.ReviewRepository;
import ru.lebruce.store.service.ProductService;
import ru.lebruce.store.service.ReviewService;
import ru.lebruce.store.service.UserService;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository repository;
    private final ProductService productService;
    private final UserService userService;

    @Override
    public Page<Review> findAllByProductId(Long productId, int page, int size, String[] sort) {
        Product product = productService.getByProductId(productId);
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.by(sort[0]).with(Sort.Direction.fromString(sort[1]))));
        return repository.findByProduct(product, pageable);
    }

    @Override
    public Review findByProductIdAndCurrentUser(Long productId) {
        Long userId = userService.getCurrentUser().getUserId();
        return repository.findByProductIdAndUserId(productId, userId).orElseThrow(() ->
                new ReviewNotFoundException("Отзыв не найден"));
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
        if (doesReviewExistForProductAndUser(reviewRequest.getProductId(), userService.getCurrentUser().getUserId())) {
            throw new ReviewAlreadyExists("Отзыв пользователя уже существует");
        }

        var review = Review.builder()
                .product(productService.getByProductId(reviewRequest.getProductId()))
                .user(userService.getCurrentUser())
                .rating(reviewRequest.getRating())
                .comment(reviewRequest.getComment())
                .build();
        repository.save(review);
    }

    private boolean doesReviewExistForProductAndUser(Long productId, Long userId) {
        return repository.findByProductIdAndUserId(productId, userId).isPresent();
    }
}
