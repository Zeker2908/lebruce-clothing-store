package ru.lebruce.store.domain.model;

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

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "product_size", indexes = {
        @Index(name = "idx_product_id", columnList = "product_id"),
        @Index(name = "idx_size", columnList = "size")
})
public class ProductSize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnore
    private Product product;

    @Column(nullable = false)
    @Size(min = 1, max = 10)
    private String size;

    @Column(nullable = false)
    @PositiveOrZero
    private Integer quantity;

    @Formula("CASE WHEN quantity > 0 THEN true ELSE false END")
    private Boolean available;

    @OneToMany(mappedBy = "size", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ShoppingCartItem> shoppingCartItems;

    @OneToMany(mappedBy = "size", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Schema(description = "Элементы заказов, содержащие продукт")
    private List<OrderItem> orderItems;
}
