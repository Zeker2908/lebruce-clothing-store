package ru.lebruce.store.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.lebruce.store.domain.model.Category;
import ru.lebruce.store.domain.model.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    void deleteByProductId(Long productId);

    void deleteByProductName(String productName);

    Optional<List<Product>> findByProductNameOrBrand(String productName, String brand);

    Optional<Product> findByProductId(Long productId);

    boolean existsByProductNameAndBrandAndCategory(String productName, String brand, Category category);

    boolean existsByProductId(Long productId);

    boolean existsByProductName(String productName);

    @Query("SELECT p FROM Product p WHERE p.category.categoryName = :categoryName")
    Page<Product> findByCategoryName(@Param("categoryName") String categoryName, Pageable pageable);

}
