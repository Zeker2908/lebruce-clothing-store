package ru.lebruce.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lebruce.store.domain.model.PasswordResetToken;
import ru.lebruce.store.domain.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);

    List<PasswordResetToken> findAllByExpiresAtBefore(LocalDateTime localDateTime);

    boolean existsByUser(User user);

    boolean existsByToken(String token);
}
