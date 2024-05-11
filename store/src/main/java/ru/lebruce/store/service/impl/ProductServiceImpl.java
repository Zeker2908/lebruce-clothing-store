package ru.lebruce.store.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lebruce.store.domain.exception.ProductAlreadyExistsException;
import ru.lebruce.store.domain.exception.ProductNotFoundException;
import ru.lebruce.store.domain.model.Product;
import ru.lebruce.store.repository.ProductRepository;
import ru.lebruce.store.service.ProductService;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;

    @Override
    public List<Product> findAllProducts() {
        return repository.findAll();
    }

    @Override
    public Product getByProductName(String productName) {
        return repository.findByProductName(productName)
                .orElseThrow(() -> new ProductNotFoundException("Товар с названием " + productName + " не найден"));

    }

    @Override
    public Product createProduct(Product product) {
        if(repository.existsByProductNameAndBrandAndCategory(product.getProductName(), product.getBrand(), product.getCategory())) {
            throw new ProductAlreadyExistsException("Такой товар уже существует");
        }
        return saveProduct(product);
    }

    @Override
    public Product saveProduct(Product product) {
        return repository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        if(!repository.existsById(product.getProductId())) {
            throw new ProductNotFoundException("Товар с ID " + product.getProductId() + " не найден");
        }
        return repository.save(product);
    }

    @Override
    @Transactional
    public void deleteProduct(Long productId) {
        if(!repository.existsByProductId(productId)) {
            throw new ProductNotFoundException("Товар с ID " + productId + " не найден");
        }
        repository.deleteByProductId(productId);
    }

    @Override
    @Transactional
    public void deleteProduct(String productName) {
        if (!repository.existsByProductName(productName)){
            throw new ProductNotFoundException("Товар с названием " + productName + " не найден");
        }
        repository.deleteByProductName(productName);
    }
}
