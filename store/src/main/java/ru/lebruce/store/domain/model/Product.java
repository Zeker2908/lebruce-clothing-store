package ru.lebruce.store.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false)
    private String productName;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false)
    @PositiveOrZero
    private double price;

    @Column(length = 1000)
    private String description;

    @Formula("(SELECT COALESCE(SUM(ps.quantity), 0) FROM product_size ps WHERE ps.product_id = product_id)")
    private int availableQuantity;

    @ElementCollection
    @Column(nullable = false)
    @Size(min = 1, max = 10)
    @UniqueElements
    private List<String> imageUrls;

    @Formula("(SELECT COALESCE(AVG(r.rating), 0) FROM reviews r WHERE r.product_id = product_id)")
    private Double rating;

    @Formula("(SELECT COUNT(*) FROM reviews r WHERE r.product_id = product_id)")
    private Long reviewCount;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Review> reviews;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Characteristic> characteristics;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductSize> sizes;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ShoppingCartItem> shoppingCartItems;
}
