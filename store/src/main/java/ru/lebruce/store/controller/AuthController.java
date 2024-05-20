package ru.lebruce.store.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.lebruce.store.domain.dto.JwtAuthenticationResponse;
import ru.lebruce.store.domain.dto.SetPasswordRequest;
import ru.lebruce.store.domain.dto.SignInRequest;
import ru.lebruce.store.domain.dto.SignUpRequest;
import ru.lebruce.store.exception.UserAlreadyExistsException;
import ru.lebruce.store.service.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Аутентификация")
public class AuthController {
    private final AuthenticationUserService authenticationService;
    private final PendingUserService pendingUserService;
    private final UserService userService;
    private final PasswordResetService passwordResetService;
    private final PasswordResetTokenService passwordResetTokenService;

    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody @Valid SignUpRequest request) throws MessagingException {
        pendingUserService.checkUserExistence(request.getUsername());
        authenticationService.signUp(request);
        return ResponseEntity.ok("На вашу почту " + request.getUsername() + " отправлено письмо");
    }

    @Operation(summary = "Авторизация пользователя")
    @PostMapping("/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest request) {
        return authenticationService.signIn(request);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/password-request")
    public ResponseEntity<?> resetPassword() throws MessagingException {
        var user = userService.getCurrentUser();
        if (passwordResetTokenService.existsByUser(user)) {
            throw new UserAlreadyExistsException("Вам уже направлено письмо");
        }
        authenticationService.resetPasswordRequest(user);
        return ResponseEntity.ok("На вашу почту отправлено письмо с дальнейшими указаниями");
    }

    @PutMapping("/password")
    public ResponseEntity<?> resetPassword(@RequestBody @Valid SetPasswordRequest passwordRequest) {
        passwordResetService.resetPassword(passwordRequest.getToken(), passwordRequest);
        return ResponseEntity.ok("Ваш пароль был успешно изменен");
    }
}

