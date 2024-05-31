package ru.lebruce.store.controller;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.lebruce.store.domain.dto.EmailDto;
import ru.lebruce.store.domain.dto.SetPasswordRequest;
import ru.lebruce.store.domain.dto.SetUsernameRequest;
import ru.lebruce.store.domain.dto.UpdateUserRequest;
import ru.lebruce.store.domain.model.SetUsernameToken;
import ru.lebruce.store.domain.model.User;
import ru.lebruce.store.exception.UserAlreadyExistsException;
import ru.lebruce.store.service.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;
    private final SetUsernameTokenService setUsernameTokenService;
    private final SetUsernameService setUsernameService;
    private final PasswordResetTokenService passwordResetTokenService;
    private final PasswordResetService passwordResetService;


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
    public ResponseEntity<?> updateUser(@RequestBody @Valid UpdateUserRequest request) {
        return ResponseEntity.ok(service.updateUser(request));
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteUsername(@PathVariable String username) {
        service.deleteUser(username);
        return ResponseEntity.ok("Пользователь " + username + " успешно удален");
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/username/set")
    public ResponseEntity<?> setUsernameRequest(@RequestBody @Valid SetUsernameRequest setUsernameRequest) throws MessagingException {
        return handleSetUsernameRequest(service.getCurrentUser(), setUsernameRequest);
    }

    @PutMapping("/username")
    public ResponseEntity<?> setUsername(@RequestBody SetUsernameToken token) {
        setUsernameService.setUsername(token.getToken());
        return ResponseEntity.ok("Ваша почта успешно изменена");
    }
    
    private ResponseEntity<?> handleSetUsernameRequest(User user, SetUsernameRequest setUsernameRequest) throws MessagingException {
        if (service.existsByUsername(setUsernameRequest.getUsername())) {
            throw new UserAlreadyExistsException("Пользователь с такой почтой существует");
        } else if (setUsernameService.existsByUser(user)) {
            throw new UserAlreadyExistsException("Вам уже направлено письмо");
        }
        setUsernameService.setUsernameRequest(user, setUsernameRequest);
        return ResponseEntity.ok("На вашу почту отправлено письмо с дальнейшими указаниями");
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("password/reset")
    public ResponseEntity<?> resetPasswordForAuthenticatedUser() throws MessagingException {
        var user = service.getCurrentUser();
        return handlePasswordResetRequest(user);
    }

    @PostMapping("password/forgot")
    public ResponseEntity<?> forgotPassword(@RequestBody @Valid EmailDto email) throws MessagingException {
        var user = service.getByUsername(email.getEmail());
        return handlePasswordResetRequest(user);
    }

    @PutMapping("/password")
    public ResponseEntity<?> resetPassword(@RequestBody @Valid SetPasswordRequest passwordRequest) {
        passwordResetService.resetPassword(passwordRequest.getToken(), passwordRequest);
        return ResponseEntity.ok("Ваш пароль был успешно изменен");
    }

    private ResponseEntity<?> handlePasswordResetRequest(User user) throws MessagingException {
        if (passwordResetTokenService.existsByUser(user)) {
            throw new UserAlreadyExistsException("Вам уже направлено письмо");
        }
        passwordResetService.resetPasswordRequest(user);
        return ResponseEntity.ok("На вашу почту отправлено письмо с дальнейшими указаниями");
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("admin/{username}")
    public void getAdmin(@PathVariable String username) {
        service.getAdmin(username);
    }
}
