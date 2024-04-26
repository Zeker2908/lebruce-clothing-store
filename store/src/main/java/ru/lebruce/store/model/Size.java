package ru.lebruce.store.model;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.PositiveOrZero;

@Data
@Entity
@Table(name = "sizes")
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sizeId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private String size;

    @PositiveOrZero
    private int quantityAvailable;
}
