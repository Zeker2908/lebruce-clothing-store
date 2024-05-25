package ru.lebruce.store.service;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import ru.lebruce.store.domain.dto.ProductRequest;
import ru.lebruce.store.domain.model.Product;

import java.io.IOException;

public interface ProductService {

    Page<Product> findAllProducts(int page, int size, String[] sort);

    Product getByProductName(String productName);

    Product saveProduct(Product product);

    Product updateProduct(Product product);

    Product createProduct(ProductRequest product, MultipartFile[] files);

    void uploadImages(Product product, MultipartFile[] files);

    void deleteProduct(Long productId);

    void deleteProduct(String productName);

    Object saveImageAndGetUrl(MultipartFile multipartFile, Product product) throws IOException;
}
