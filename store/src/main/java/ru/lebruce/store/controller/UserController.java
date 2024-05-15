package ru.lebruce.store.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lebruce.store.domain.dto.JwtAuthenticationResponse;
import ru.lebruce.store.domain.dto.SetPasswordRequest;
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

    @PutMapping("/password")
    public JwtAuthenticationResponse setPassword(@RequestBody @Valid SetPasswordRequest request) {
        return authenticationService.setPassword(request);
    }

    @PutMapping()
    public JwtAuthenticationResponse updateUser(@RequestBody @Valid UpdateUserRequest request) {
        return authenticationService.updateUser(request);
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

    //todo как все настрою раскомментировать
    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("get_admin/{username}")
    public void getAdmin(@PathVariable String username) {
        service.getAdmin(username);
    }
}
