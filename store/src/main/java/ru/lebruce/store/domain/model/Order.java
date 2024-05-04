package ru.lebruce.store.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    @PrePersist
    protected void onCreate() {
        orderDate = new Date();
    }
}
