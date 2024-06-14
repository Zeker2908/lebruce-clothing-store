package ru.lebruce.store.controller;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.lebruce.store.domain.dto.CharacteristicRequest;
import ru.lebruce.store.domain.dto.ProductRequest;
import ru.lebruce.store.domain.dto.ProductSizeRequest;
import ru.lebruce.store.domain.dto.ReviewRequest;
import ru.lebruce.store.domain.model.Characteristic;
import ru.lebruce.store.domain.model.Product;
import ru.lebruce.store.domain.model.ProductSize;
import ru.lebruce.store.domain.model.Review;
import ru.lebruce.store.service.*;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService service;
    private final CharacteristicService characteristicService;
    private final ProductSizeService productSizeService;
    private final ReviewService reviewService;
    private final BrandService brandService;

    /**
     * Возвращает все товары
     *
     * @param page Номер страницы
     * @param size Количество товаров на странице
     * @param sort Сортировка товаров
     * @return Страницу с товарами, заданного размера, с возможностью сортировки
     */
    @GetMapping
    public ResponseEntity<?> findAllProducts(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "20") int size,
                                             @RequestParam(defaultValue = "productId,asc") String[] sort) {
        Page<Product> productPage = service.findAllProducts(page, size, sort);
        return ResponseEntity.ok(productPage);
    }

    /**
     * Возвращает все товары по категории
     *
     * @param categoryName Название категории
     * @param page         Номер страницы
     * @param size         Количество товаров на странице
     * @param sort         Сортировка товаров
     * @return Отсортированную страницу с выбранной категорией
     */
    @GetMapping("/category")
    public ResponseEntity<?> findAllProductsByCategory(@RequestParam String categoryName,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "20") int size,
                                                       @RequestParam(defaultValue = "productId,asc") String[] sort) {
        Page<Product> productPage = service.findAllProductsByCategory(categoryName, page, size, sort);
        return ResponseEntity.ok(productPage);
    }

    /**
     * Возвращает все товары по бренду
     *
     * @param brandName Название бренда
     * @param page      Номер страницы
     * @param size      Количество товаров на странице
     * @param sort      Сортировка товаров
     * @return Отсортированную страницу с выбранным брендом
     */
    @GetMapping("/brand")
    public ResponseEntity<?> findAllProductsByBrand(@RequestParam String brandName,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "20") int size,
                                                    @RequestParam(defaultValue = "productId,asc") String[] sort) {
        Page<Product> productPage = service.findAllProductsByBrand(brandName, page, size, sort);
        return ResponseEntity.ok(productPage);
    }

    /**
     * Возвращает товар по ID
     *
     * @param id ID товара
     * @return найденный товар
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findByProductId(@PathVariable Long id) {
        return ResponseEntity.ok(service.getByProductId(id));
    }

    /**
     * Поиск товаров
     *
     * @param query Поисковый запрос
     * @param page  Номер страницы
     * @param size  Количество товаров на странице
     * @param sort  Сортировка товаров
     * @return Найденные товары
     */
    @GetMapping("/search")
    public ResponseEntity<Page<Product>> search(@RequestParam String query,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "5") int size,
                                                @RequestParam(defaultValue = "reviewCount,desc") String[] sort) {
        Page<Product> productPage = service.searchProducts(query, page, size, sort);
        return ResponseEntity.ok(productPage);
    }

    /**
     * Создает новый товар
     *
     * @param productRequest Данные о товаре
     * @param images         Изображения товара
     * @return Созданный товар
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createProduct(@Valid ProductRequest productRequest, @RequestPart MultipartFile[] images) {
        return ResponseEntity.ok(service.createProduct(productRequest, images));
    }

    /**
     * Обновляет товар
     *
     * @param product Данные о товаре
     * @return Обновленный товар
     */
    @PutMapping
    public ResponseEntity<?> updateProduct(@RequestBody @Valid Product product) {
        return ResponseEntity.ok(service.updateProduct(product));
    }

    /**
     * Удаляет товар по ID
     *
     * @param productId ID товара
     * @return ответ, что товар успешно удален
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        service.deleteProduct(productId);
        return ResponseEntity.ok("Товар с ID " + productId + " успешно удален");
    }

    /**
     * Удаляет товар по названию
     *
     * @param productName Название товара
     * @return ответ, что товар успешно удален
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/name/{productName}")
    public ResponseEntity<?> deleteProductByName(@PathVariable String productName) {
        service.deleteProduct(productName);
        return ResponseEntity.ok("Товар с названием " + productName + " успешно удален");
    }

    // Методы, которые относятся к характеристикам товаров

    /**
     * Создает новую характеристику товара
     *
     * @param characteristic Данные о характеристике
     * @return Characteristic
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/characteristic")
    public Characteristic create(@RequestBody @Valid CharacteristicRequest characteristic) {
        return characteristicService.create(characteristic);
    }

    /**
     * Обновляет характеристику товара
     *
     * @param characteristic Данные о характеристике
     * @return Characteristic
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/characteristic")
    public Characteristic updateCharacteristic(@RequestBody Characteristic characteristic) {
        return characteristicService.update(characteristic);
    }

    /**
     * Удаляет характеристику товара по ID
     *
     * @param characteristicId ID характеристики
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/characteristic/{characteristicId}")
    public void deleteCharacteristic(@PathVariable Long characteristicId) {
        characteristicService.deleteCharacteristic(characteristicId);
    }

// Методы, которые относятся к размерам товаров

    /**
     * Создает новый размер товара
     *
     * @param productSizeRequest Данные о размере
     * @return ResponseEntity<?>
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/size")
    public ResponseEntity<?> createProductSize(@RequestBody @Valid ProductSizeRequest productSizeRequest) {
        productSizeService.createProductSize(productSizeRequest);
        return ResponseEntity.ok("Размер успешно создан");
    }

    /**
     * Обновляет размер товара
     *
     * @param productSize Данные о размере
     * @return ResponseEntity<?>
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/size")
    public ResponseEntity<?> updateProductSize(@RequestBody ProductSize productSize) {
        productSizeService.updateProductSize(productSize);
        return ResponseEntity.ok("Размер обновлен");
    }

    /**
     * Удаляет размер товара по ID
     *
     * @param id ID размера
     * @return ResponseEntity<?>
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/size/{id}")
    public ResponseEntity<?> deleteProductSize(@PathVariable Long id) {
        productSizeService.deleteProductSize(id);
        return ResponseEntity.ok("Размер успешно удален");
    }

// Методы, которые относятся к отзывам товаров

    /**
     * Возвращает все отзывы о товаре
     *
     * @param productId ID товара
     * @param page      Номер страницы
     * @param size      Количество элементов на странице
     * @param sort      Параметры сортировки
     * @return ResponseEntity<Page < Review>>
     */
    @GetMapping("/review")
    public ResponseEntity<Page<Review>> getReviews(@RequestParam Long productId,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size,
                                                   @RequestParam(defaultValue = "reviewId,asc") String[] sort) {
        return ResponseEntity.ok(reviewService.findAllByProductId(productId, page, size, sort));
    }

    /**
     * Возвращает отзыв о товаре текущего пользователя
     *
     * @param productId ID товара
     * @return ResponseEntity<Review>
     */
    @GetMapping("/review/{productId}")
    public ResponseEntity<Review> getReview(@PathVariable Long productId) {
        return ResponseEntity.ok(reviewService.findByProductIdAndCurrentUser(productId));
    }

    /**
     * Создает новый отзыв о товаре
     *
     * @param review Данные об отзыве
     * @return ResponseEntity<?>
     */
    @PostMapping("/review")
    public ResponseEntity<?> createReview(@RequestBody @Valid ReviewRequest review) {
        reviewService.createReview(review);
        return ResponseEntity.ok("Отзыв успешно оставлен");
    }

    /**
     * Удаляет отзыв о товаре
     *
     * @param id Id отзыва
     * @return ResponseEntity<?>
     */
    @DeleteMapping("/review/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.ok("Отзыв успешно удален");
    }

}
