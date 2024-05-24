package ru.lebruce.store.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lebruce.store.domain.dto.ReviewRequest;
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
