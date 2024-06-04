package ru.lebruce.store.service;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import ru.lebruce.store.domain.dto.ProductRequest;
import ru.lebruce.store.domain.model.Product;

import java.util.List;

public interface ProductService {

    Page<Product> findAllProducts(int page, int size, String[] sort);

    Page<Product> findAllProductsByCategory(String categoryName, int page, int size, String[] sort);

    Page<Product> findAllProductsByBrand(String brandName, int page, int size, String[] sort);

    Page<Product> searchProducts(String query, int page, int size, String[] sort);

    Product getByProductId(Long productId);

    Product saveProduct(Product product);

    Product updateProduct(Product product);

    Product createProduct(ProductRequest product, MultipartFile[] files);

    List<String> uploadImages(MultipartFile[] files);

    void deleteProduct(Long productId);

    void deleteProduct(String productName);
}
