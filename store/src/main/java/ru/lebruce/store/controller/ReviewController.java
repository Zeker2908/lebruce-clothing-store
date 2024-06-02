package ru.lebruce.store.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lebruce.store.domain.dto.ReviewRequest;
import ru.lebruce.store.domain.model.Review;
import ru.lebruce.store.service.ReviewService;

@RestController
@RequestMapping("api/v1/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService service;

    @GetMapping
    public ResponseEntity<Page<Review>> getReviews(@RequestParam Long productId,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size,
                                                   @RequestParam(defaultValue = "reviewId,asc") String[] sort) {
        return ResponseEntity.ok(service.findAllByProductId(productId, page, size, sort));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Review> getReview(@PathVariable Long productId) {
        return ResponseEntity.ok(service.findByProductIdAndCurrentUser(productId));
    }

    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody @Valid ReviewRequest review) {
        service.createReview(review);
        return ResponseEntity.ok("Отзыв успешно оставлен");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Long id) {
        service.deleteReview(id);
        return ResponseEntity.ok("Отзыв успешно удален");
    }
}
