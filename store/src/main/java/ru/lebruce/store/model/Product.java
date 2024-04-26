package ru.lebruce.store.model;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;


@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String productName;
    private String brand;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Positive
    private double price;

    private String description;

    @PositiveOrZero
    private int availableQuantity;

    private String imageUrl;
}
