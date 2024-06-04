package ru.lebruce.store.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lebruce.store.domain.model.Brand;
import ru.lebruce.store.service.BrandService;

@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                     @RequestParam(defaultValue = "name,asc") String[] sort) {
        return ResponseEntity.ok(brandService.findAll(page, size, sort));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Brand>> searchBrand(@RequestParam String query,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "5") int size,
                                                   @RequestParam(defaultValue = "name,asc") String[] sort) {
        Page<Brand> productPage = brandService.search(query, page, size, sort);
        return ResponseEntity.ok(productPage);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Brand brand) {
        return ResponseEntity.ok(brandService.saveBrand(brand));
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Brand brand) {
        return ResponseEntity.ok(brandService.updateBrand(brand));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        brandService.deleteById(id);
        return ResponseEntity.ok("Бренд успешно удален");
    }

}
