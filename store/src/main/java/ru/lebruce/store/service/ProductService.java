package ru.lebruce.store.service;

import org.springframework.data.domain.Page;
import ru.lebruce.store.domain.dto.ProductRequest;
import ru.lebruce.store.domain.model.Product;

public interface ProductService {

    Page<Product> findAllProducts(int page, int size, String[] sort);

    Product getByProductName(String productName);

    Product saveProduct(Product product);

    Product updateProduct(Product product);

    Product createProduct(ProductRequest product);
    
    void deleteProduct(Long productId);

    void deleteProduct(String productName);

}
