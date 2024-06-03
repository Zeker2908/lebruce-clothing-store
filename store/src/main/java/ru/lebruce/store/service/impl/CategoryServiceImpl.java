package ru.lebruce.store.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.lebruce.store.domain.model.Category;
import ru.lebruce.store.repository.CategoryRepository;
import ru.lebruce.store.service.CategoryService;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;

    @Override
    public List<Category> findAllCategories() {
        return repository.findAll();
    }

    @Override
    public Category findByCategoryName(String categoryName) {
        return repository.findByCategoryName(categoryName);
    }

    @Override
    public Page<Category> searchCategory(String categoryName, Pageable pageable) {
        return repository.findByCategoryNameContainingIgnoreCase(categoryName, pageable);
    }

    @Override
    public Category saveCategory(Category category) {
        return repository.save(category);
    }

    @Override
    public Category updateCategory(Category category) {
        return repository.save(category);
    }

    @Override
    @Transactional
    public void deleteCategory(Long categoryId) {
        repository.deleteByCategoryId(categoryId);
    }

    @Override
    @Transactional
    public void deleteCategory(String categoryName) {
        repository.deleteByCategoryName(categoryName);
    }
}
