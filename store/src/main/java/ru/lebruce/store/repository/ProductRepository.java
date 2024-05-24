package ru.lebruce.store.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.lebruce.store.domain.model.Category;
import ru.lebruce.store.domain.model.Product;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    void deleteByProductId(Long productId);

    void deleteByProductName(String productName);

    Optional<Product> findByProductName(String productName);

    Optional<Product> findByProductId(Long productId);

    boolean existsByProductNameAndBrandAndCategory(String productName, String brand, Category category);

    boolean existsByProductId(Long productId);

    boolean existsByProductName(String productName);

    @Transactional
    @Modifying
    @Query("UPDATE Product p SET p.averageRating = :averageRating WHERE p.productId = :productId")
    void updateAverageRating(@Param("productId") Long productId, @Param("averageRating") Double averageRating);

}
