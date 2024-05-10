package ru.lebruce.store.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;


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

    @Column(nullable = false)
    private double price;

    private String description;

    @Column(nullable = false)
    private int availableQuantity;

    @ElementCollection
    private List<String> imageUrl;
}
