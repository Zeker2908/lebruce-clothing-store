package ru.lebruce.store.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lebruce.store.domain.dto.ProductSizeRequest;
import ru.lebruce.store.domain.model.ProductSize;
import ru.lebruce.store.exception.ProductSizeAlreadyExistsException;
import ru.lebruce.store.exception.ProductSizeNotFoundException;
import ru.lebruce.store.repository.ProductSizeRepository;
import ru.lebruce.store.service.ProductService;
import ru.lebruce.store.service.ProductSizeService;

@Service
@RequiredArgsConstructor
public class ProductSizeServiceImpl implements ProductSizeService {

    private final ProductSizeRepository repository;
    private final ProductService productService;

    @Override
    public void createProductSize(ProductSizeRequest productSizeRequest) {
        if (repository.existsByProduct_ProductIdAndSize(productSizeRequest.getProductId(), productSizeRequest.getSize())) {
            throw new ProductSizeAlreadyExistsException("Такой размер уже сущетсвует");
        }
        repository.save(ProductSize.builder()
                .product(productService.getByProductId(productSizeRequest.getProductId()))
                .size(productSizeRequest.getSize())
                .quantity(productSizeRequest.getQuantity())
                .build());
    }

    @Override
    public ProductSize getProductSizeById(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new ProductSizeNotFoundException("Такого размера не сущестует"));
    }

    @Override
    public void updateProductSize(ProductSize productSize) {
        if (repository.findById(productSize.getId()).isEmpty()) {
            throw new ProductSizeNotFoundException("Такого размера не сущестует");
        }
        repository.save(productSize);
    }

    @Override
    public void updateQuantity(Long ProductSizeId, int quantityChange) {
        ProductSize productSize = getProductSizeById(ProductSizeId);
        productSize.setQuantity(productSize.getQuantity() + quantityChange);
        updateProductSize(productSize);
    }


    @Transactional
    @Override
    public void deleteProductSize(Long id) {
        ProductSize productSize = repository.findById(id).orElseThrow(() ->
                new ProductSizeNotFoundException("Такого размера не сущестует"));
        repository.delete(productSize);
    }
}
