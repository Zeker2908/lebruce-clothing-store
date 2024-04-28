package ru.lebruce.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lebruce.store.model.Review;

import java.util.Date;

public interface ReviewsRepository extends JpaRepository<Review,Long> {
    Review findByReviewRating(Double reviewRating); //Cannot resolve property ....
    Review findByReviewDate(Date reviewDate); //Cannot resolve property
    void deleteReview(Long reviewId);
}
