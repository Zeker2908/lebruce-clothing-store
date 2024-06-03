package ru.lebruce.store.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.lebruce.store.domain.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAllCategories();

    Category findByCategoryName(String categoryName);

    Page<Category> searchCategory(String categoryName, Pageable pageable);

    Category saveCategory(Category category);

    Category updateCategory(Category category);

    void deleteCategory(Long categoryId);

    void deleteCategory(String categoryName);
}
