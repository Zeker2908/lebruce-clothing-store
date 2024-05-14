package ru.lebruce.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lebruce.store.domain.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    void deleteByUsername(String username);

    void deleteByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByUserId(Long userId);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUserId(Long userId);
}
