package ru.lebruce.store.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.lebruce.store.domain.exception.UserAlreadyExistsException;
import ru.lebruce.store.domain.exception.UserNotFoundException;
import ru.lebruce.store.domain.model.User;
import ru.lebruce.store.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping
    public ResponseEntity<?> findAllUser() {
        return ResponseEntity.ok(service.findAllUsers());
    }

    @GetMapping("email/{email}")
    public ResponseEntity<?> getByEmail(@PathVariable String email) {
        return ResponseEntity.ok(service.getByEmail(email));
    }

    @GetMapping("username/{username}")
    public ResponseEntity<?> getByUsername(@PathVariable String username) {
        return ResponseEntity.ok(service.getByUsername(username));
    }

    @PutMapping()
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        return ResponseEntity.ok(service.updateUser(user));
    }

    @DeleteMapping("username/{username}")
    public ResponseEntity<?> deleteUsername(@PathVariable String username) {
        service.deleteUsername(username);
        return ResponseEntity.ok("Пользователь " + username + " успешно удален");
    }

    @DeleteMapping("email/{email}")
    public ResponseEntity<?> deleteUser(@PathVariable String email) {
        service.deleteUser(email);
        return ResponseEntity.ok("Пользователь " + email + " успешно удален");
    }
}
