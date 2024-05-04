package ru.lebruce.store.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
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
    public Product findByProductName(String productName) {
        return repository.findByProductName(productName);
    }

    @Override
    public Product saveProduct(Product product) {
        return repository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        return repository.save(product);
    }

    @Override
    @Transactional
    public void deleteProduct(Long productId) {
        repository.deleteByProductId(productId);
    }

    @Override
    @Transactional
    public void deleteProduct(String productName) {
        repository.deleteByProductName(productName);
    }
}
