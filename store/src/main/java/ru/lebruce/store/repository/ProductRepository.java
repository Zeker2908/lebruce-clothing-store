package ru.lebruce.store.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.lebruce.store.domain.model.Brand;
import ru.lebruce.store.domain.model.Category;
import ru.lebruce.store.domain.model.Product;

import java.util.Optional;

/**
 * Репозиторий для управления сущностями продуктов.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Удаляет продукт по его идентификатору.
     *
     * @param productId идентификатор продукта
     */
    void deleteByProductId(Long productId);

    /**
     * Удаляет продукт по его названию.
     *
     * @param productName название продукта
     */
    void deleteByProductName(String productName);

    /**
     * Находит продукт по его идентификатору.
     *
     * @param productId идентификатор продукта
     * @return Optional, содержащий найденный продукт, если такой продукт существует
     */
    Optional<Product> findByProductId(Long productId);

    /**
     * Находит продукты по частичному совпадению названия продукта или названия бренда.
     *
     * @param productName частичное название продукта
     * @param brandName   частичное название бренда
     * @param pageable    информация о разбиении на страницы
     * @return страница с продуктами, удовлетворяющими критериям поиска
     */
    Page<Product> findByProductNameContainingIgnoreCaseOrBrand_NameContainingIgnoreCase(String productName, String brandName, Pageable pageable);

    /**
     * Проверяет, существует ли продукт с заданным названием, брендом и категорией.
     *
     * @param productName название продукта
     * @param brand       бренд продукта
     * @param category    категория продукта
     * @return true, если продукт существует, иначе false
     */
    boolean existsByProductNameAndBrandAndCategory(String productName, Brand brand, Category category);

    /**
     * Проверяет, существует ли продукт с заданным идентификатором.
     *
     * @param productId идентификатор продукта
     * @return true, если продукт существует, иначе false
     */
    boolean existsByProductId(Long productId);

    /**
     * Проверяет, существует ли продукт с заданным названием.
     *
     * @param productName название продукта
     * @return true, если продукт существует, иначе false
     */
    boolean existsByProductName(String productName);

    /**
     * Находит продукты по названию бренда.
     *
     * @param name     название бренда
     * @param pageable информация о разбиении на страницы
     * @return страница с продуктами, удовлетворяющими критериям поиска
     */
    Page<Product> findByBrand_Name(String name, Pageable pageable);

    /**
     * Находит продукты по названию категории.
     *
     * @param categoryName название категории
     * @param pageable     информация о разбиении на страницы
     * @return страница с продуктами, удовлетворяющими критериям поиска
     */
    @Query("SELECT p FROM Product p WHERE p.category.categoryName = :categoryName")
    Page<Product> findByCategoryName(@Param("categoryName") String categoryName, Pageable pageable);

}
