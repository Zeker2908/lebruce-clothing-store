package ru.lebruce.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD
import ru.lebruce.store.domain.model.Product;
=======
import org.springframework.stereotype.Repository;
import ru.lebruce.store.model.Product;
>>>>>>> main

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    void deleteByProductId(Long productId);

    void deleteByProductName(String productName);

    Product findByProductName(String productName);

}
