package ru.lebruce.store.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.lebruce.store.domain.model.Category;
import ru.lebruce.store.service.CategoryService;

@RestController
@RequestMapping("/api/v1/categories")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService service;

    @GetMapping
    public Page<Category> findAllCategory(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size,
                                          @RequestParam(defaultValue = "categoryName,asc") String[] sort) {
        return service.findAllCategories(page, size, sort);
    }

    @GetMapping("/{categoryName}")
    public Category findByCategoryName(@PathVariable String categoryName) {
        return service.findByCategoryName(categoryName);
    }

    @GetMapping("/search")
    public Page<Category> searchCategory(@RequestParam @Valid String query) {
        Pageable pageable = PageRequest.of(0, 5);
        return service.searchCategory(query, pageable);
    }

    @PostMapping
    public Category saveCategory(@RequestBody Category category) {
        return service.saveCategory(category);
    }

    @PutMapping
    public Category updateCategory(@RequestBody Category category) {
        return service.updateCategory(category);
    }

    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable Long categoryId) {
        service.deleteCategory(categoryId);
    }

    @DeleteMapping("name/{categoryName}")
    public void deleteCategory(@PathVariable String categoryName) {
        service.deleteCategory(categoryName);
    }
}
