package ru.lebruce.store.service;

import ru.lebruce.store.domain.dto.CreateProductRequest;
import ru.lebruce.store.domain.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAllProducts();

    Product getByProductName(String productName);

    Product saveProduct(Product product);

    Product updateProduct(Product product);

    Product createProduct(CreateProductRequest product);

    Double getAverageRatingForProduct(Long productId);

    void deleteProduct(Long productId);

    void deleteProduct(String productName);

}
