package ru.lebruce.store.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Min(0)
    @Max(5)
    @Column(nullable = false)
    private double rating;

    private String comment;
    private LocalDateTime datePosted;

    @PrePersist
    protected void onCreate() {
        datePosted = LocalDateTime.now();
    }
}
