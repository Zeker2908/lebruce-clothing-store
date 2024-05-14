package ru.lebruce.store.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lebruce.store.domain.dto.UpdateUserRequest;
import ru.lebruce.store.domain.model.User;
import ru.lebruce.store.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
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

    @GetMapping("current")
    public ResponseEntity<?> getCurrentUser() {
        return ResponseEntity.ok(service.getCurrentUser());
    }

    @PutMapping()
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserRequest user) {
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
