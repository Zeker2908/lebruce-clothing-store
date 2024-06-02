package ru.lebruce.store.service;

import ru.lebruce.store.domain.dto.ProductSizeRequest;
import ru.lebruce.store.domain.model.ProductSize;

public interface ProductSizeService {
    void createProductSize(ProductSizeRequest productSize);

    void updateProductSize(ProductSize productSize);

    void deleteProductSize(Long id);
}
