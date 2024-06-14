package ru.lebruce.store.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;

/**
 * Модель продукта.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "products", indexes = {
        @Index(name = "idx_product_productName", columnList = "productName"),
        @Index(name = "idx_product_price", columnList = "price")
})
@Schema(description = "Модель продукта")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Идентификатор продукта", example = "1")
    private Long productId;

    @Column(nullable = false)
    @Schema(description = "Название продукта", example = "Ноутбук")
    private String productName;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    @Schema(description = "Бренд продукта")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @Schema(description = "Категория продукта")
    private Category category;

    @Column(nullable = false)
    @PositiveOrZero
    @Schema(description = "Цена продукта", example = "999.99")
    private double price;

    @Column(length = 1000)
    @Schema(description = "Описание продукта", example = "Этот ноутбук обладает отличной производительностью и стилем.")
    private String description;

    @Formula("(SELECT COALESCE(SUM(ps.quantity), 0) FROM product_size ps WHERE ps.product_id = product_id)")
    @Schema(description = "Доступное количество продукта", example = "50")
    private int availableQuantity;

    @ElementCollection
    @Column(nullable = false)
    @Size(min = 1, max = 10)
    @UniqueElements
    @Schema(description = "Список URL-адресов изображений продукта")
    private List<String> imageUrls;

    @Formula("(SELECT COALESCE(AVG(r.rating), 0) FROM reviews r WHERE r.product_id = product_id)")
    @Schema(description = "Рейтинг продукта", example = "4.5")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT, pattern = "#.##")
    private Double rating;

    @Formula("(SELECT COUNT(*) FROM reviews r WHERE r.product_id = product_id)")
    @Schema(description = "Количество отзывов о продукте", example = "120")
    private Long reviewCount;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Schema(description = "Отзывы о продукте")
    private List<Review> reviews;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Характеристики продукта")
    private List<Characteristic> characteristics;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id ASC")
    @Schema(description = "Размеры продукта")
    private List<ProductSize> sizes;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Schema(description = "Элементы корзины покупок, содержащие продукт")
    private List<ShoppingCartItem> shoppingCartItems;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Schema(description = "Элементы заказов, содержащие продукт")
    private List<OrderItem> orderItems;

}
