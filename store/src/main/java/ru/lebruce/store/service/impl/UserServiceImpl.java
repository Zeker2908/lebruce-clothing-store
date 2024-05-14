package ru.lebruce.store.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.lebruce.store.domain.dto.UpdateUserRequest;
import ru.lebruce.store.exception.UserAlreadyExistsException;
import ru.lebruce.store.exception.UserNotFoundException;
import ru.lebruce.store.domain.model.User;
import ru.lebruce.store.repository.UserRepository;
import ru.lebruce.store.service.UserService;
import ru.lebruce.store.domain.model.Role;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String USER_NOT_FOUND_MESSAGE = "Пользователь не найден";

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
    public User updateUser(UpdateUserRequest userRequest) {
        User user = repository.findByUserId(userRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundException("Пользователь с ID " + userRequest.getUserId() + " не найден"));

        Optional.ofNullable(userRequest.getEmail())
                .filter(email -> !email.isEmpty())
                .filter(email -> !repository.existsByEmail(email))
                .ifPresent(user::setEmail);
        Optional.ofNullable(userRequest.getFirstName())
                .filter(firstName -> !firstName.isEmpty())
                .ifPresent(user::setFirstName);
        Optional.ofNullable(userRequest.getLastName())
                .filter(lastName -> !lastName.isEmpty())
                .ifPresent(user::setLastName);


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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            return repository.findByUsername(userDetails.getUsername()).orElseThrow(()->
                    new UserNotFoundException(USER_NOT_FOUND_MESSAGE));
        } else {
            throw new AuthenticationCredentialsNotFoundException("Пользователь не аутентифицирован");
        }
    }


    /**
     * Выдача прав администратора текущему пользователю
     * <p>
     * Нужен для демонстрации
     */
    @Override
    public void getAdmin(String username) {
        var user = getByUsername(username);
        user.setRole(Role.ROLE_ADMIN);
        saveUser(user);
    }
}
