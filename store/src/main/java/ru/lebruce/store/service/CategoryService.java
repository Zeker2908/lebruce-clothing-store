package ru.lebruce.store.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.lebruce.store.domain.model.Category;

public interface CategoryService {
    Page<Category> findAllCategories(int page, int size, String[] sort);

    Category findByCategoryName(String categoryName);

    Page<Category> searchCategory(String categoryName, Pageable pageable);

    Category saveCategory(Category category);

    Category updateCategory(Category category);

    void deleteCategory(Long categoryId);

    void deleteCategory(String categoryName);
}
