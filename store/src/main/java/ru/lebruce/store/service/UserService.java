package ru.lebruce.store.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.lebruce.store.domain.model.User;

import java.util.List;

public interface UserService {
    List<User> findAllUsers();

    User saveUser(User user);

    User create(User user);

    User getByEmail(String email);

    User updateUser(User user);

    User getByUsername(String username);

    UserDetailsService userDetailsService();
    
    void deleteUser(Long userId);

    void deleteUser(String email);

    User getCurrentUser();

    void getAdmin();
}
