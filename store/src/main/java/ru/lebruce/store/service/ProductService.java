package ru.lebruce.store.service;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import ru.lebruce.store.domain.dto.ProductRequest;
import ru.lebruce.store.domain.model.Product;

import java.util.List;

/**
 * Сервис для управления продуктами.
 */
public interface ProductService {

    /**
     * Находит все продукты с пагинацией и сортировкой.
     *
     * @param page номер страницы
     * @param size размер страницы
     * @param sort параметры сортировки
     * @return страница с продуктами
     */
    Page<Product> findAllProducts(int page, int size, String[] sort);

    /**
     * Находит все продукты по категории с пагинацией и сортировкой.
     *
     * @param categoryName название категории
     * @param page         номер страницы
     * @param size         размер страницы
     * @param sort         параметры сортировки
     * @return страница с продуктами
     */
    Page<Product> findAllProductsByCategory(String categoryName, int page, int size, String[] sort);

    /**
     * Находит все продукты по бренду с пагинацией и сортировкой.
     *
     * @param brandName название бренда
     * @param page      номер страницы
     * @param size      размер страницы
     * @param sort      параметры сортировки
     * @return страница с продуктами
     */
    Page<Product> findAllProductsByBrand(String brandName, int page, int size, String[] sort);

    /**
     * Осуществляет поиск продуктов по запросу с пагинацией и сортировкой.
     *
     * @param query поисковой запрос
     * @param page  номер страницы
     * @param size  размер страницы
     * @param sort  параметры сортировки
     * @return страница с продуктами, удовлетворяющими критериям поиска
     */
    Page<Product> searchProducts(String query, int page, int size, String[] sort);

    /**
     * Находит продукт по его идентификатору.
     *
     * @param productId идентификатор продукта
     * @return найденный продукт
     */
    Product getByProductId(Long productId);

    /**
     * Сохраняет новый продукт.
     *
     * @param product продукт для сохранения
     * @return сохраненный продукт
     */
    Product saveProduct(Product product);

    /**
     * Обновляет существующий продукт.
     *
     * @param productRequest продукт для обновления
     * @return обновленный продукт
     */
    Product updateProduct(Long productId, ProductRequest productRequest, MultipartFile[] images);

    /**
     * Создает новый продукт и загружает изображения.
     *
     * @param product данные нового продукта
     * @param files   массив изображений для загрузки
     * @return созданный продукт
     */
    Product createProduct(ProductRequest product, MultipartFile[] files);

    /**
     * Загружает изображения для продукта.
     *
     * @param files массив изображений для загрузки
     * @return список URL-адресов загруженных изображений
     */
    List<String> uploadImages(MultipartFile[] files);

    /**
     * Удаляет продукт по его идентификатору.
     *
     * @param productId идентификатор продукта
     */
    void deleteProduct(Long productId);

    /**
     * Удаляет продукт по его названию.
     *
     * @param productName название продукта
     */
    void deleteProduct(String productName);
}
