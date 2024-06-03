package ru.lebruce.store.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.lebruce.store.domain.model.Category;
import ru.lebruce.store.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService service;

    @GetMapping
    public List<Category> findAllCategory() {
        return service.findAllCategories();
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
