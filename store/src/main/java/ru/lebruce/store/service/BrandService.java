package ru.lebruce.store.service;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import ru.lebruce.store.domain.model.Brand;

public interface BrandService {
    Page<Brand> findAll(int page, int size, String[] sort);

    Page<Brand> search(String query, int page, int size, String[] sort);

    Brand getById(Long id);

    Brand saveBrand(Brand brand);

    Brand updateBrand(Brand brand);

    @Transactional
    void deleteById(Long id);
}
