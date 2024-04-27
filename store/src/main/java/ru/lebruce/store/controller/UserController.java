package ru.lebruce.store.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.lebruce.store.model.User;
import ru.lebruce.store.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {
    private UserService service;

    @GetMapping
    public List<User> findAllUser() {
        return service.findAllUsers();
    }

    public User saveUser(User user) {
        return service.saveUser(user);
    }

    public User findByEmail(String email) {
        return service.findByEmail(email);
    }

    public User updateUser(User user) {
        return service.updateUser(user);
    }

    public void deleteUser(String email) {
        service.deleteUser(email);
    }
}
