package ru.lebruce.store.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lebruce.store.domain.model.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    Page<Brand> findByNameContainingIgnoreCase(String name, Pageable pageable);

    boolean existsByName(String name);

    boolean existsByNameIgnoreCase(String name);
}
