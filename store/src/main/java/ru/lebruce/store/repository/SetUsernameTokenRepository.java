package ru.lebruce.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lebruce.store.domain.model.SetUsernameToken;
import ru.lebruce.store.domain.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SetUsernameTokenRepository extends JpaRepository<SetUsernameToken, Long> {

    Optional<SetUsernameToken> findByToken(String token);

    boolean existsByUser(User user);

    boolean existsByToken(String token);

    List<SetUsernameToken> findAllByExpiresAtBefore(LocalDateTime expiresAt);
}
