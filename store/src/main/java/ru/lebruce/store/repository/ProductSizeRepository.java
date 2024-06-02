package ru.lebruce.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lebruce.store.domain.model.ProductSize;

@Repository
public interface ProductSizeRepository extends JpaRepository<ProductSize, Long> {
    boolean existsByProduct_ProductIdAndSize(Long productId, String productSize);
}
