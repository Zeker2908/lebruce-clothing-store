package ru.lebruce.store.controller;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
    public List<Product> findAllProducts() { return service.findAllProducts(); }

    @GetMapping("/{productName}")
    public ResponseEntity<?> findByProductName(@PathVariable String productName){
        try {
            return ResponseEntity.ok(service.getByProductName(productName));
        }catch (ProductNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Товар с именем " + productName + " не найден");
        }
    }

    @PostMapping("create")
    public ResponseEntity<?> createProduct(@RequestBody @Valid Product product) {
        try {
            return ResponseEntity.ok(service.createProduct(product));
        }catch (ProductAlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("update")
    public ResponseEntity<?> updateProduct(@RequestBody @Valid Product product) {
        try {
            return ResponseEntity.ok(service.updateProduct(product));
        }catch (ProductNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @DeleteMapping("delete_by_id/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        try {
            service.deleteProduct(productId);
            return ResponseEntity.ok("Товар с ID " + productId + " успешно удален");
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("delete_by_name/{productName}")
    public ResponseEntity<?> deleteProduct(@PathVariable String productName){
        try {
            service.deleteProduct(productName);
            return ResponseEntity.ok("Товар с названием " + productName + " успешно удален");
        }catch (ProductNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
