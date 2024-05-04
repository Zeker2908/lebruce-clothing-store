package ru.lebruce.store.domain.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "characteristics")
public class Characteristic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long characteristicId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private String characteristicName;
    private String characteristicValue;
}
