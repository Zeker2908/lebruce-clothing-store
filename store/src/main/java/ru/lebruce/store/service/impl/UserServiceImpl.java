package ru.lebruce.store.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.lebruce.store.domain.exception.UserAlreadyExistsException;
import ru.lebruce.store.domain.exception.UserNotFoundException;
import ru.lebruce.store.domain.model.User;
import ru.lebruce.store.repository.UserRepository;
import ru.lebruce.store.service.UserService;
import ru.lebruce.store.domain.model.Role;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;


    @Override
    public List<User> findAllUsers() {
        return repository.findAll();
    }

    @Override
    public User saveUser(User user) {
        return repository.save(user);
    }

    /**
     * Получение пользователя по почте
     *<p>
     * @return  пользователь
     */
    @Override
    public User getByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
    }

    @Override
    public User updateUser(User user) {
        if(!repository.existsByUserId(user.getUserId())) {
            throw new UserNotFoundException("Пользователь с ID " + user.getUserId() + " не существует");
        }
        return repository.save(user);
    }

    @Override
    @Transactional
    public void deleteUsername(String username) {
        if(!repository.existsByUsername(username)) {
            throw new UserNotFoundException("Пользователь " + username + " не существует");
        }
        repository.deleteByUsername(username);
    }

    @Override
    @Transactional
    public void deleteUser(String email) {
        if(!repository.existsByEmail(email)) {
            throw new UserNotFoundException("Пользователя с почтой " + email + " не существует");
        }
        repository.deleteByEmail(email);
    }

    /**
     * Создание пользователя
     *<p>
     * @return созданный пользователь
     */
    @Override
    public User create(User user) {
        if (repository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException("Пользователь с таким именем уже существует");
        }else if (repository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("Пользователь с таким email уже существует");
        }

        return saveUser(user);
    }

    /**
     * Получение пользователя по имени пользователя
     *<p>
     * @return пользователь
     */
    @Override
    public User getByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

    }

    /**
     * Получение пользователя по имени пользователя
     * <p>
     * Нужен для Spring Security
     *
     * @return пользователь
     */
    @Override
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    /**
     * Получение текущего пользователя
     *<p>
     * @return текущий пользователь
     */
    @Override
    public User getCurrentUser() {
        // Получение имени пользователя из контекста Spring Security
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }


    /**
     * Выдача прав администратора текущему пользователю
     * <p>
     * Нужен для демонстрации
     */
    @Override
    @Deprecated
    public void getAdmin() {
        var user = getCurrentUser();
        user.setRole(Role.ROLE_ADMIN);
        saveUser(user);
    }
}
