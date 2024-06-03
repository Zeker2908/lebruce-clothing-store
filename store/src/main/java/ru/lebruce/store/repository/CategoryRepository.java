package ru.lebruce.store.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lebruce.store.domain.model.Category;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    void deleteByCategoryId(Long categoryId);

    void deleteByCategoryName(String categoryName);

    Category findByCategoryName(String categoryName);

    boolean existsByCategoryName(String categoryName);

    Optional<Category> findByCategoryId(Long categoryId);

    Page<Category> findByCategoryNameContainingIgnoreCase(String categoryName, Pageable pageable);
}
