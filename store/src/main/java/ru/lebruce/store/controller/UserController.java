package ru.lebruce.store.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.lebruce.store.domain.dto.JwtAuthenticationResponse;
import ru.lebruce.store.domain.dto.UpdateUserRequest;
import ru.lebruce.store.service.AuthenticationUserService;
import ru.lebruce.store.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;
    private final AuthenticationUserService authenticationService;

    @GetMapping
    public ResponseEntity<?> findAllUser() {
        return ResponseEntity.ok(service.findAllUsers());
    }


    @GetMapping("/{username}")
    public ResponseEntity<?> getByUsername(@PathVariable String username) {
        return ResponseEntity.ok(service.getByUsername(username));
    }

    @GetMapping("current")
    public ResponseEntity<?> getCurrentUser() {
        return ResponseEntity.ok(service.getCurrentUser());
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping
    public JwtAuthenticationResponse updateUser(@RequestBody @Valid UpdateUserRequest request) {
        return service.updatedUser(request);
    }

    //todo решить проблему удаления
    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteUsername(@PathVariable String username) {
        service.deleteUser(username);
        return ResponseEntity.ok("Пользователь " + username + " успешно удален");
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("admin/{username}")
    public void getAdmin(@PathVariable String username) {
        service.getAdmin(username);
    }
}
