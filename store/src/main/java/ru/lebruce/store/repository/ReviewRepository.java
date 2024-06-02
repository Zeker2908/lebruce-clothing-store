package ru.lebruce.store.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.lebruce.store.domain.model.Product;
import ru.lebruce.store.domain.model.Review;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r WHERE r.product.productId = :productId AND r.user.userId = :userId")
    Optional<Review> findByProductIdAndUserId(@Param("productId") Long productId, @Param("userId") Long userId);

    Page<Review> findByProduct(Product product, Pageable pageable);

    void deleteByReviewId(Long reviewId);
}
