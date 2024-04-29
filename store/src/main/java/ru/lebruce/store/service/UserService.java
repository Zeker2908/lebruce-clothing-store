package ru.lebruce.store.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.lebruce.store.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    List<User> findAllUsers();

    User saveUser(User user);

    User findByEmail(String email);

    Optional<User> findByUsername(String username);

    User updateUser(User user);
    
    void deleteUser(Long userId);

    void deleteUser(String email);
}
