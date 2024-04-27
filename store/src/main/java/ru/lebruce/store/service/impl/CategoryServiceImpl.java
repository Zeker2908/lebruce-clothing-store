package ru.lebruce.store.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lebruce.store.model.Category;
import ru.lebruce.store.repository.CategoryRepository;
import ru.lebruce.store.service.CategoryService;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository repository;

    @Override
    public List<Category> findAllCategory() {
        return repository.findAll();
    }

    @Override
    public Category findByCategoryName(String categoryName) {
        return repository.findByCategoryName(categoryName);
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
