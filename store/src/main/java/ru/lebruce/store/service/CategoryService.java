package ru.lebruce.store.service;

import ru.lebruce.store.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAllCategory();

    Category findByCategoryName(String categoryName);

    Category saveCategory(Category category);

    Category updateCategory(Category category);

    void deleteCategory(Long categoryId);

    void deleteCategory(String categoryName);
}
