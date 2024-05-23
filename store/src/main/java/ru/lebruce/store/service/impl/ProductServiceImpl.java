package ru.lebruce.store.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lebruce.store.domain.dto.ProductRequest;
import ru.lebruce.store.domain.model.Product;
import ru.lebruce.store.exception.CategoryNotFoundException;
import ru.lebruce.store.exception.ProductAlreadyExistsException;
import ru.lebruce.store.exception.ProductNotFoundException;
import ru.lebruce.store.repository.CategoryRepository;
import ru.lebruce.store.repository.ProductRepository;
import ru.lebruce.store.repository.ReviewRepository;
import ru.lebruce.store.service.ProductService;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    private final ReviewRepository reviewRepository;
    private final CategoryRepository categoryRepository;

    private static final String PRODUCT_NOT_FOUND = "Товар с названием %s не найден";
    private static final String PRODUCT_ALREADY_EXISTS_MESSAGE = "Такой товар уже существует";

    @Override
    public List<Product> findAllProducts() {
        return repository.findAll();
    }

    @Override
    public Product getByProductName(String productName) {
        return repository.findByProductName(productName)
                .orElseThrow(() -> new ProductNotFoundException(String.format(PRODUCT_NOT_FOUND, productName)));
    }

    @Override
    public Product createProduct(ProductRequest productRequest) {
        if (repository.existsByProductNameAndBrandAndCategory(productRequest.getProductName(), productRequest.getBrand(),
                categoryRepository.findByCategoryId(productRequest.getCategoryId()).orElseThrow(() ->
                        new CategoryNotFoundException("Данной категории не существует")))) {
            throw new ProductAlreadyExistsException(PRODUCT_ALREADY_EXISTS_MESSAGE);
        }

        var product = Product.builder()
                .productName(productRequest.getProductName())
                .brand(productRequest.getBrand())
                .category(categoryRepository.findByCategoryId(productRequest.getCategoryId()).orElseThrow(() ->
                        new CategoryNotFoundException("Данной категории не существует")))
                .price(productRequest.getPrice())
                .description(productRequest.getDescription())
                .availableQuantity(productRequest.getAvailableQuantity())
                .imageUrls(productRequest.getImageUrls())
                .build();

        return saveProduct(product);
    }

    @Override
    public Product saveProduct(Product product) {
        return repository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        if (!repository.existsById(product.getProductId())) {
            throw new ProductNotFoundException(String.format(PRODUCT_NOT_FOUND, product.getProductId()));
        }
        return repository.save(product);
    }

    @Override
    @Transactional
    public void deleteProduct(Long productId) {
        if (!repository.existsByProductId(productId)) {
            throw new ProductNotFoundException(String.format(PRODUCT_NOT_FOUND, productId));
        }
        repository.deleteByProductId(productId);
    }

    @Override
    @Transactional
    public void deleteProduct(String productName) {
        if (!repository.existsByProductName(productName)) {
            throw new ProductNotFoundException(String.format(PRODUCT_NOT_FOUND, productName));
        }
        repository.deleteByProductName(productName);
    }

    @Override
    public Double getAverageRatingForProduct(Long productId) {
        if (!repository.existsByProductId(productId)) {
            throw new ProductNotFoundException(String.format(PRODUCT_NOT_FOUND, productId));
        }
        return reviewRepository.findAverageRatingByProductId(productId);
    }
}
