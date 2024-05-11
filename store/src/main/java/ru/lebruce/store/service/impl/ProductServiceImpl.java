package ru.lebruce.store.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lebruce.store.domain.exception.ProductAlreadyExistsException;
import ru.lebruce.store.domain.exception.ProductNotFoundException;
import ru.lebruce.store.domain.model.Product;
import ru.lebruce.store.repository.ProductRepository;
import ru.lebruce.store.repository.ReviewRepository;
import ru.lebruce.store.service.ProductService;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    private final ReviewRepository reviewRepository;

    private static final String PRODUCT_NOT_FOUND_MESSAGE_BY_NAME = "Товар с названием %s не найден";
    private static final String PRODUCT_NOT_FOUND_MESSAGE_BY_ID = "Товар с ID %d не найден";
    private static final String PRODUCT_ALREADY_EXISTS_MESSAGE = "Такой товар уже существует";

    @Override
    public List<Product> findAllProducts() {
        return repository.findAll();
    }

    @Override
    public Product getByProductName(String productName) {
        return repository.findByProductName(productName)
                .orElseThrow(() -> new ProductNotFoundException(String.format(PRODUCT_NOT_FOUND_MESSAGE_BY_NAME, productName)));
    }

    @Override
    public Product createProduct(Product product) {
        if (repository.existsByProductNameAndBrandAndCategory(product.getProductName(), product.getBrand(), product.getCategory())) {
            throw new ProductAlreadyExistsException(PRODUCT_ALREADY_EXISTS_MESSAGE);
        }
        return saveProduct(product);
    }

    @Override
    public Product saveProduct(Product product) {
        return repository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        if (!repository.existsById(product.getProductId())) {
            throw new ProductNotFoundException(String.format(PRODUCT_NOT_FOUND_MESSAGE_BY_ID, product.getProductId()));
        }
        return repository.save(product);
    }

    @Override
    @Transactional
    public void deleteProduct(Long productId) {
        if (!repository.existsByProductId(productId)) {
            throw new ProductNotFoundException(String.format(PRODUCT_NOT_FOUND_MESSAGE_BY_ID, productId));
        }
        repository.deleteByProductId(productId);
    }

    @Override
    @Transactional
    public void deleteProduct(String productName) {
        if (!repository.existsByProductName(productName)) {
            throw new ProductNotFoundException(String.format(PRODUCT_NOT_FOUND_MESSAGE_BY_NAME, productName));
        }
        repository.deleteByProductName(productName);
    }

    @Override
    public Double getAverageRatingForProduct(Long productId) {
        if (!repository.existsByProductId(productId)) {
            throw new ProductNotFoundException(String.format(PRODUCT_NOT_FOUND_MESSAGE_BY_ID, productId));
        }
        return reviewRepository.findAverageRatingByProductId(productId);
    }
}
