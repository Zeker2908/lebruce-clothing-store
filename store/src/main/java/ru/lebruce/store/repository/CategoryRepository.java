package ru.lebruce.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD
import ru.lebruce.store.domain.model.Category;
=======
import org.springframework.stereotype.Repository;
import ru.lebruce.store.model.Category;
>>>>>>> main

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    void deleteByCategoryId(Long categoryId);

    void deleteByCategoryName(String categoryName);

    Category findByCategoryName(String categoryName);
}
