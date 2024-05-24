package ru.lebruce.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
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


}
