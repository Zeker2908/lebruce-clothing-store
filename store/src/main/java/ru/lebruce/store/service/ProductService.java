package ru.lebruce.store.service;

import ru.lebruce.store.model.Category;
import ru.lebruce.store.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAllProducts();

    Product findByProductName(String productName);

    Product saveProduct(Product product);

    Product updateProduct(Product product);

    void deleteProduct(Long productId);

    void deleteProduct(String productName);

}
