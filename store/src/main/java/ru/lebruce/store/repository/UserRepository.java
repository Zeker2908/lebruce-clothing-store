package ru.lebruce.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lebruce.store.domain.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    void deleteByUserId(Long userId);

    void deleteByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
