package ru.lebruce.store.controller;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.lebruce.store.domain.dto.EmailDto;
import ru.lebruce.store.domain.dto.SetPasswordRequest;
import ru.lebruce.store.domain.model.PasswordResetToken;
import ru.lebruce.store.domain.model.User;
import ru.lebruce.store.exception.UserAlreadyExistsException;
import ru.lebruce.store.service.PasswordResetService;
import ru.lebruce.store.service.PasswordResetTokenService;
import ru.lebruce.store.service.UserService;

@Controller
@RequestMapping("/api/v1/password")
@RequiredArgsConstructor
public class ResetPasswordController {
    private final PasswordResetTokenService passwordResetTokenService;
    private final UserService userService;
    private final PasswordResetService passwordResetService;


    @GetMapping
    public String resetPasswordPage(@RequestParam String token, Model model) {
        PasswordResetToken passwordResetToken = passwordResetTokenService.getToken(token);
        passwordResetTokenService.expires(passwordResetToken);
        model.addAttribute("token", token);
        return "reset-password";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/reset")
    public ResponseEntity<?> resetPasswordForAuthenticatedUser() throws MessagingException {
        var user = userService.getCurrentUser();
        return handlePasswordResetRequest(user);
    }

    @PostMapping("/forgot")
    public ResponseEntity<?> forgotPassword(@RequestBody @Valid EmailDto email) throws MessagingException {
        var user = userService.getByUsername(email.getEmail());
        return handlePasswordResetRequest(user);
    }

    @PutMapping
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
}
