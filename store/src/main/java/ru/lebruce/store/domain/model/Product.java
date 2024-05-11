package ru.lebruce.store.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;


@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private String brand;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false)
    @PositiveOrZero
    private double price;

    private String description;

    @Column(nullable = false)
    @PositiveOrZero
    private int availableQuantity;

    @ElementCollection
    @Column(nullable = false)
    @Size(min = 1, max = 10)
    private List<String> imageUrls;
}
