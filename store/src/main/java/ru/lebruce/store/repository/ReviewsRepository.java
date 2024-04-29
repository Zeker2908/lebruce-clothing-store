package ru.lebruce.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lebruce.store.model.Review;

import java.util.Date;


public interface ReviewsRepository extends JpaRepository<Review,Long> {
    Review findByRating(Double reviewRating);
    Review findByDatePosted(Date reviewDate);
    void deleteByDatePosted(Date datePosted);
    void deleteByReviewId(Long reviewId);
}
