package ru.lebruce.store.controller;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.lebruce.store.model.Order;
import ru.lebruce.store.model.Product;
import ru.lebruce.store.model.User;
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
    public Product findByProductName(@PathVariable String productName){return service.findByProductName(productName);}

    @PostMapping("save_product")
    public Product saveProduct(@RequestBody Product product) {
        return service.saveProduct(product);
    }

    @PutMapping("update_product")
    public Product updateProduct(@RequestBody Product product) {
        return service.updateProduct(product);
    }

    @DeleteMapping("delete_product_by_id/{productId}")
    public void deleteProduct(@PathVariable Long productId) {
        service.deleteProduct(productId);
    }

    @DeleteMapping("delete_product_by_name/{productName}")
    public void deleteProduct(@PathVariable String productName){ service.deleteProduct(productName); }

}
