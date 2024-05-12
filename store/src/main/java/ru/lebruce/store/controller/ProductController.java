package ru.lebruce.store.controller;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lebruce.store.domain.dto.CreateProductRequest;
import ru.lebruce.store.domain.model.Product;
import ru.lebruce.store.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService service;

    @GetMapping
    public ResponseEntity<?> findAllProducts() {
        return ResponseEntity.ok(service.findAllProducts());
    }

    @GetMapping("/{productName}")
    public ResponseEntity<?> findByProductName(@PathVariable String productName) {
        return ResponseEntity.ok(service.getByProductName(productName));
    }

    @GetMapping("/average-rating/{productId}")
    public ResponseEntity<?> getAverageRatingForProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(service.getAverageRatingForProduct(productId));
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody @Valid CreateProductRequest product) {
        return ResponseEntity.ok(service.createProduct(product));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Long productId, @RequestBody @Valid Product product) {
        product.setProductId(productId);
        return ResponseEntity.ok(service.updateProduct(product));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        service.deleteProduct(productId);
        return ResponseEntity.ok("Товар с ID " + productId + " успешно удален");
    }

    @DeleteMapping("/name/{productName}")
    public ResponseEntity<?> deleteProductByName(@PathVariable String productName) {
        service.deleteProduct(productName);
        return ResponseEntity.ok("Товар с названием " + productName + " успешно удален");
    }

}
