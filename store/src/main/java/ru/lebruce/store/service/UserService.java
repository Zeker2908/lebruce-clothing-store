package ru.lebruce.store.service;

import org.springframework.security.core.userdetails.UserDetailsService;
<<<<<<< HEAD
import ru.lebruce.store.domain.model.User;
=======
import ru.lebruce.store.model.User;
>>>>>>> main

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    List<User> findAllUsers();

    User saveUser(User user);

    User create(User user);

    User findByEmail(String email);

    Optional<User> findByUsername(String username);

    User updateUser(User user);

    User getByUsername(String username);

    UserDetailsService userDetailsService();
    
    void deleteUser(Long userId);

    void deleteUser(String email);

<<<<<<< HEAD
    User getCurrentUser();

    void getAdmin();
=======
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

>>>>>>> main
}
