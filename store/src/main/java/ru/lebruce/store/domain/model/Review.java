package ru.lebruce.store.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    @Range(min = 1, max = 5)
    @Column(nullable = false)
    private int rating;

    @Column(length = 300)
    private String comment;

    private LocalDateTime datePosted;

    @PrePersist
    protected void onCreate() {
        datePosted = LocalDateTime.now();
    }
}
