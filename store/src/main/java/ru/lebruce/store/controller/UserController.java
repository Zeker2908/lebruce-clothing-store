
package ru.lebruce.store.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.lebruce.store.domain.model.User;
import ru.lebruce.store.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping
    public List<User> findAllUser() {
        return service.findAllUsers();
    }

    @GetMapping("/get_by_email/{email}")
    public ResponseEntity<?> getByEmail(@PathVariable String email) {
        try {
            var user = service.getByEmail(email);
            return ResponseEntity.ok(user);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь с почтой " + email + " не найден");
        }
    }

    @GetMapping("get_by_username/{username}")
    public ResponseEntity<?> getByUsername(@PathVariable String username) {
        try {
            var user = service.getByUsername(username);
            return ResponseEntity.ok(user);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь с именем " + username + " не найден");
        }
    }

    @PutMapping("update_user")
    public User updateUser(@RequestBody User user) {
        return service.updateUser(user);
    }

    @DeleteMapping("delete_user_by_username/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUsername(@PathVariable String username) {
        service.deleteUsername(username);
    }

    @DeleteMapping("delete_user_by_email/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String email) {
        service.deleteUser(email);
    }

}
