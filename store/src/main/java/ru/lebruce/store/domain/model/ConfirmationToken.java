package ru.lebruce.store.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "confirmation_tokens")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(nullable = false, name = "pending_user_id")
    private PendingUser user;

}
