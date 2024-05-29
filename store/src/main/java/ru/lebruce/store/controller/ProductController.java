package ru.lebruce.store.controller;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.lebruce.store.domain.dto.ProductRequest;
import ru.lebruce.store.domain.model.Product;
import ru.lebruce.store.service.ProductService;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService service;

    @GetMapping
    public ResponseEntity<?> findAllProducts(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size,
                                             @RequestParam(defaultValue = "productId,asc") String[] sort) {
        Page<Product> productPage = service.findAllProducts(page, size, sort);
        return ResponseEntity.ok(productPage);
    }


    @GetMapping("/{productName}")
    public ResponseEntity<?> findByProductName(@PathVariable String productName) {
        return ResponseEntity.ok(service.getByProductName(productName));
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid ProductRequest productRequest, @RequestPart MultipartFile[] images) {
        return ResponseEntity.ok(service.createProduct(productRequest, images));
    }

    @PutMapping
    public ResponseEntity<?> updateProduct(@RequestBody @Valid Product product) {
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
