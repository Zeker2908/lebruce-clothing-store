package ru.lebruce.store.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.lebruce.store.domain.dto.ProductRequest;
import ru.lebruce.store.domain.model.Product;
import ru.lebruce.store.exception.BrandNotFoundException;
import ru.lebruce.store.exception.CategoryNotFoundException;
import ru.lebruce.store.exception.ProductNotFoundException;
import ru.lebruce.store.repository.BrandRepository;
import ru.lebruce.store.repository.CategoryRepository;
import ru.lebruce.store.repository.ProductRepository;
import ru.lebruce.store.service.BrandService;
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
    private final BrandService brandService;
    private final BrandRepository brandRepository;

    private static final String PRODUCT_NOT_FOUND = "Товар с названием %s не найден";

    @Override
    public Page<Product> findAllProducts(int page, int size, String[] sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.by(sort[0]).with(Sort.Direction.fromString(sort[1]))));
        return repository.findAll(pageable);
    }

    @Override
    public Page<Product> findAllProductsByCategory(String categoryName, int page, int size, String[] sort) {
        if (!categoryRepository.existsByCategoryName(categoryName)) {
            throw new CategoryNotFoundException("Категория не найдена");
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.by(sort[0]).with(Sort.Direction.fromString(sort[1]))));
        return repository.findByCategoryName(categoryName, pageable);
    }

    @Override
    public Page<Product> findAllProductsByBrand(String brandName, int page, int size, String[] sort) {
        if (!brandRepository.existsByName(brandName)) {
            throw new BrandNotFoundException("Бренд не найдена");
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.by(sort[0]).with(Sort.Direction.fromString(sort[1]))));
        return repository.findByBrand_Name(brandName, pageable);
    }

    @Override
    public Page<Product> searchProducts(String query, int page, int size, String[] sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.by(sort[0]).with(Sort.Direction.fromString(sort[1]))));
        return repository.findByProductNameContainingIgnoreCaseOrBrand_NameContainingIgnoreCase(query, query, pageable);
    }


    @Override
    public Product getByProductId(Long productId) {
        return repository.findByProductId(productId).orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND));
    }

    @Override
    public Product createProduct(ProductRequest productRequest, MultipartFile[] images) {


        var product = Product.builder()
                .productName(productRequest.getProductName())
                .brand(brandService.getById(productRequest.getBrandId()))
                .category(categoryRepository.findByCategoryId(productRequest.getCategoryId()).orElseThrow(() ->
                        new CategoryNotFoundException("Данной категории не существует")))
                .price(productRequest.getPrice())
                .description(productRequest.getDescription())
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
