package ru.lebruce.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lebruce.store.domain.model.PendingUser;

import java.util.Optional;

@Repository
public interface PendingUserRepository extends JpaRepository<PendingUser, Long> {
    Optional<PendingUser> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
