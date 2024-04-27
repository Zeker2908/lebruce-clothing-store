package ru.lebruce.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lebruce.store.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    void deleteByEmail(String email);

    User findByEmail(String email);
}
