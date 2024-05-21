package ru.lebruce.store.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.lebruce.store.domain.dto.JwtAuthenticationResponse;
import ru.lebruce.store.domain.dto.UpdateUserRequest;
import ru.lebruce.store.domain.model.User;

import java.util.List;

public interface UserService {
    List<User> findAllUsers();

    User saveUser(User user);

    User create(User user);

    User updateUser(UpdateUserRequest user);

    JwtAuthenticationResponse updatedUser(UpdateUserRequest userRequest);

    User getByUsername(String username);

    UserDetailsService userDetailsService();

    void deleteUser(String username);

    User getCurrentUser();

    void getAdmin(String username);

    boolean existsByUsername(String username);
}
