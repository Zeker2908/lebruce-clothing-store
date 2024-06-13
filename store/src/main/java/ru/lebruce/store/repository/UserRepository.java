package ru.lebruce.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lebruce.store.domain.model.User;

import java.util.Optional;

/**
 * Репозиторий для управления сущностями пользователей.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Удаляет пользователя по имени пользователя.
     *
     * @param username имя пользователя
     */
    void deleteByUsername(String username);

    /**
     * Находит пользователя по имени пользователя.
     *
     * @param username имя пользователя
     * @return Optional, содержащий найденного пользователя, если такой пользователь существует
     */
    Optional<User> findByUsername(String username);

    /**
     * Проверяет, существует ли пользователь с заданным именем пользователя.
     *
     * @param username имя пользователя
     * @return true, если пользователь существует, иначе false
     */
    boolean existsByUsername(String username);
}
