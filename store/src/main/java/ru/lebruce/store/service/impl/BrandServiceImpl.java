package ru.lebruce.store.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.lebruce.store.domain.model.Brand;
import ru.lebruce.store.exception.BrandNotFoundException;
import ru.lebruce.store.repository.BrandRepository;
import ru.lebruce.store.service.BrandService;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;

    @Override
    public Page<Brand> findAll(int page, int size, String[] sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.by(sort[0]).with(Sort.Direction.fromString(sort[1]))));
        return brandRepository.findAll(pageable);
    }

    @Override
    public Page<Brand> search(String query, int page, int size, String[] sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.by(sort[0]).with(Sort.Direction.fromString(sort[1]))));
        return brandRepository.findByNameContainingIgnoreCase(query, pageable);
    }

    @Override
    public Brand getById(Long id) {
        return brandRepository.findById(id).orElseThrow(() ->
                new BrandNotFoundException("Бренд не найден"));
    }

    @Override
    public Brand saveBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public Brand updateBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!brandRepository.existsById(id)) {
            throw new BrandNotFoundException("Бренд не найден");
        }
        brandRepository.deleteById(id);
    }
}
