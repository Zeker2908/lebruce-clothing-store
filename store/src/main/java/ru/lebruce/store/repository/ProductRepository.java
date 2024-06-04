package ru.lebruce.store.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.lebruce.store.domain.model.Brand;
import ru.lebruce.store.domain.model.Category;
import ru.lebruce.store.domain.model.Product;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    void deleteByProductId(Long productId);

    void deleteByProductName(String productName);

    Optional<Product> findByProductId(Long productId);

    Page<Product> findByProductNameContainingIgnoreCaseOrBrand_NameContainingIgnoreCase(String productName, String brandName, Pageable pageable);

    boolean existsByProductNameAndBrandAndCategory(String productName, Brand brand, Category category);

    boolean existsByProductId(Long productId);

    boolean existsByProductName(String productName);

    Page<Product> findByBrand_Name(String name, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.category.categoryName = :categoryName")
    Page<Product> findByCategoryName(@Param("categoryName") String categoryName, Pageable pageable);

}
