package ru.lebruce.store.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.lebruce.store.domain.dto.UpdateUserRequest;
import ru.lebruce.store.domain.model.User;

import java.util.List;

/**
 * Сервис для управления пользователями.
 */
public interface UserService {

    /**
     * Находит всех пользователей.
     *
     * @return список всех пользователей
     */
    List<User> findAllUsers();

    /**
     * Сохраняет нового пользователя.
     *
     * @param user пользователь для сохранения
     * @return сохраненный пользователь
     */
    User saveUser(User user);

    /**
     * Создает нового пользователя.
     *
     * @param user пользователь для создания
     * @return созданный пользователь
     */
    User create(User user);

    /**
     * Обновляет информацию о пользователе.
     *
     * @param user объект запроса на обновление пользователя
     * @return обновленный пользователь
     */
    User updateUser(UpdateUserRequest user);

    /**
     * Получает пользователя по имени пользователя.
     *
     * @param username имя пользователя
     * @return пользователь с заданным именем пользователя
     */
    User getByUsername(String username);

    /**
     * Получает сервис для работы с деталями пользователя.
     *
     * @return сервис для работы с деталями пользователя
     */
    UserDetailsService userDetailsService();

    /**
     * Удаляет пользователя по имени пользователя.
     *
     * @param username имя пользователя
     */
    void deleteUser(String username);

    /**
     * Получает текущего авторизованного пользователя.
     *
     * @return текущий пользователь
     */
    User getCurrentUser();

    /**
     * Получает администратора по имени пользователя.
     *
     * @param username имя пользователя
     */
    void getAdmin(String username);

    /**
     * Проверяет, существует ли пользователь с заданным именем пользователя.
     *
     * @param username имя пользователя
     * @return true, если пользователь существует, иначе false
     */
    boolean existsByUsername(String username);
}
