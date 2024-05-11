package ru.lebruce.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lebruce.store.domain.model.Category;
import ru.lebruce.store.domain.model.Product;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    void deleteByProductId(Long productId);

    void deleteByProductName(String productName);

    Optional<Product> findByProductName(String productName);

    boolean existsByProductNameAndBrandAndCategory(String productName, String brand, Category category);

    boolean existsByProductId(Long productId);

    boolean existsByProductName(String productName);

}
