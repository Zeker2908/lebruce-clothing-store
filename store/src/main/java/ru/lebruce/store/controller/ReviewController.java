package ru.lebruce.store.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.lebruce.store.model.Review;
import ru.lebruce.store.service.ReviewService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/review")
@AllArgsConstructor
public class ReviewController {


    private final ReviewService service;

    @GetMapping
    public List<Review> findAllReview() {
        return service.findAllReview();
    }

    @PostMapping("save_review")
    public Review saveUser(@RequestBody Review review) {
        return service.saveReview(review);
    }

    @GetMapping("/{date}")
    public Review findByDate(@PathVariable Date date) {
        return service.findByReviewDate(date);
    }

    @GetMapping("/{rating}")
    public Review findByRating(@PathVariable Double rating) {
        return service.findByReviewRating(rating);
    }
    @PutMapping("update_review")
    public Review updateReview(@RequestBody Review review) {
        return service.updateReview(review);
    }

    @DeleteMapping("delete_review_by_id/{reviewId}")
    public void deleteReview(@PathVariable Long reviewId) {
        service.deleteReview(reviewId);
    }

}
