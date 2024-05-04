package ru.lebruce.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lebruce.store.domain.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    void deleteByProductId(Long productId);

    void deleteByProductName(String productName);

    Product findByProductName(String productName);

}
