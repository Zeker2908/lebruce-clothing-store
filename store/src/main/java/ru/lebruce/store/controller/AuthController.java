package ru.lebruce.store.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.lebruce.store.domain.dto.*;
import ru.lebruce.store.service.AuthenticationService;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Аутентификация")
public class AuthController {
    private final AuthenticationService authenticationService;

    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/sign-up")
    public JwtAuthenticationResponse signUp(@RequestBody @Valid SignUpRequest request) {
        return authenticationService.signUp(request);
    }

    @Operation(summary = "Авторизация пользователя")
    @PostMapping("/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest request) {
        return authenticationService.signIn(request);
    }

    @PutMapping("/password")
    public JwtAuthenticationResponse setPassword(@RequestBody @Valid SetPasswordRequest request) {
        return authenticationService.setPassword(request);
    }

    @PutMapping("/user")
    public JwtAuthenticationResponse updateUser(@RequestBody @Valid UpdateUserRequest request) {
        return authenticationService.updateUser(request);
    }
}

