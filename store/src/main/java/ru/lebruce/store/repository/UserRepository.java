package ru.lebruce.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD
import ru.lebruce.store.domain.model.User;

import java.util.Optional;
=======
import org.springframework.stereotype.Repository;
import ru.lebruce.store.model.User;
>>>>>>> main

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    void deleteByUserId(Long userId);

    void deleteByEmail(String email);

    User findByEmail(String email);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
