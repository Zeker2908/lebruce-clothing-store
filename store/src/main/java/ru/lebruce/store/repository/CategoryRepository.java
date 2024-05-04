package ru.lebruce.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lebruce.store.domain.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    void deleteByCategoryId(Long categoryId);

    void deleteByCategoryName(String categoryName);

    Category findByCategoryName(String categoryName);
}
