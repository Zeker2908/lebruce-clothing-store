package ru.lebruce.store.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Data
@Entity
@Table(name = "order_details")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderDetailId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @PositiveOrZero
    private int quantity;

    @Positive
    private double unitPrice;

    @Enumerated(EnumType.STRING)
    private OrderDetailStatus status;
}
