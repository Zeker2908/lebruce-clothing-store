package ru.lebruce.store.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.lebruce.store.domain.dto.ProductRequest;
import ru.lebruce.store.domain.model.Product;
import ru.lebruce.store.exception.CategoryNotFoundException;
import ru.lebruce.store.exception.ProductAlreadyExistsException;
import ru.lebruce.store.exception.ProductNotFoundException;
import ru.lebruce.store.repository.CategoryRepository;
import ru.lebruce.store.repository.ProductRepository;
import ru.lebruce.store.service.ProductService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;

    private static final String PRODUCT_NOT_FOUND = "Товар с названием %s не найден";
    private static final String PRODUCT_ALREADY_EXISTS_MESSAGE = "Такой товар уже существует";

    @Override
    public Page<Product> findAllProducts(int page, int size, String[] sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.by(sort[0]).with(Sort.Direction.fromString(sort[1]))));
        return repository.findAll(pageable);
    }

    @Override
    public Page<Product> findAllProductsByCategory(String categoryName, int page, int size, String[] sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.by(sort[0]).with(Sort.Direction.fromString(sort[1]))));
        return repository.findByCategoryName(categoryName, pageable);
    }

    @Override
    public Product getByProductName(String productName) {
        return repository.findByProductName(productName).orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND));
    }

    @Override
    public Product getByProductId(Long productId) {
        return repository.findByProductId(productId).orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND));
    }

    @Override
    public Product createProduct(ProductRequest productRequest, MultipartFile[] images) {
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
                .imageUrls(uploadImages(images))
                .build();

        return saveProduct(product);
    }

    @SneakyThrows
    @Override
    public List<String> uploadImages(MultipartFile[] images) {
        if (images.length > 10) {
            throw new IllegalArgumentException("Количество фотографий больше 10");
        }

        return Arrays.stream(images)
                .map(image -> {
                    try {
                        return saveImageAndGetUrl(image);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
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

    private String saveImageAndGetUrl(MultipartFile image) throws IOException {
        Path imageDirectory = Paths.get("var/www/lebruce.ru/product-images");
        if (!Files.exists(imageDirectory)) {
            Files.createDirectories(imageDirectory);
        }

        String fileName = UUID.randomUUID() + "." + Objects.requireNonNull(image.getOriginalFilename()).substring(image.getOriginalFilename().lastIndexOf(".") + 1);
        Path filePath = Paths.get(imageDirectory.toString(), fileName);
        try (InputStream inputStream = image.getInputStream()) {
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }

        return "/product-images/" + fileName;
    }

}
