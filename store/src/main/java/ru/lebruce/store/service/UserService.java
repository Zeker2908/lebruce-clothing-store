package ru.lebruce.store.service;

import ru.lebruce.store.model.User;

import java.util.List;

public interface UserService {
    List<User> findAllUsers();

    User saveUser(User user);

    User findByEmail(String email);

    User updateUser(User user);
    
    void deleteUser(Long userId);

    void deleteUser(String email);
}
