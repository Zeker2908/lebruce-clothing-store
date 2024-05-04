package ru.lebruce.store.controller;

import lombok.AllArgsConstructor;
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

    @PostMapping("save_category")
    public Category saveCategory(@RequestBody Category category) {
        return service.saveCategory(category);
    }

    @PutMapping("update_category")
    public Category updateCategory(@RequestBody Category category) {
        return service.updateCategory(category);
    }

    @DeleteMapping("delete_category_by_id/{categoryId}")
    public void deleteCategory(@PathVariable Long categoryId) {
        service.deleteCategory(categoryId);
    }

    @DeleteMapping("delete_category_by_name/{categoryName}")
    public void deleteCategory(@PathVariable String categoryName) {
        service.deleteCategory(categoryName);
    }
}
