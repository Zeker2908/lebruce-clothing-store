package ru.lebruce.store.controller;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lebruce.store.domain.exception.ProductAlreadyExistsException;
import ru.lebruce.store.domain.exception.ProductNotFoundException;
import ru.lebruce.store.domain.model.Product;
import ru.lebruce.store.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService service;

    @GetMapping
    public List<Product> findAllProducts() {
        return service.findAllProducts();
    }

    @GetMapping("/{productName}")
    public ResponseEntity<?> findByProductName(@PathVariable String productName) {
        return ResponseEntity.ok(service.getByProductName(productName));
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody @Valid Product product) {
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

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<?> handleProductNotFoundException(ProductNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<?> handleProductAlreadyExistsException(ProductAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
