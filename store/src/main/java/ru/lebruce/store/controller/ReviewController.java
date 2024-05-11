package ru.lebruce.store.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lebruce.store.domain.exception.ReviewAlreadyExists;
import ru.lebruce.store.domain.model.Review;
import ru.lebruce.store.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("api/v1/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService service;

    @GetMapping
    public List<Review> getReviews() {
        return service.findAll();
    }

    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody @Valid Review review) {
        return ResponseEntity.ok(service.createReview(review));
    }

    @ExceptionHandler(ReviewAlreadyExists.class)
    public ResponseEntity<?> handleReviewAlreadyExists(ReviewAlreadyExists ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}
