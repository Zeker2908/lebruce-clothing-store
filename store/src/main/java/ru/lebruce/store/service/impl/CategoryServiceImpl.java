package ru.lebruce.store.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.lebruce.store.domain.model.Category;
import ru.lebruce.store.repository.CategoryRepository;
import ru.lebruce.store.service.CategoryService;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;

    @Override
    public Page<Category> findAllCategories(int page, int size, String[] sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.by(sort[0]).with(Sort.Direction.fromString(sort[1]))));
        return repository.findAll(pageable);
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
