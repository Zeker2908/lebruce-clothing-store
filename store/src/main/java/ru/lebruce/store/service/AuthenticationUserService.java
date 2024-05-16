package ru.lebruce.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.lebruce.store.domain.dto.*;
import ru.lebruce.store.domain.model.ConfirmationToken;
import ru.lebruce.store.domain.model.Role;
import ru.lebruce.store.domain.model.User;
import ru.lebruce.store.exception.EmailNotConfirmException;
import ru.lebruce.store.exception.TokenExpiredException;
import ru.lebruce.store.exception.TokenNotFoundException;
import ru.lebruce.store.service.impl.DefaultEmailService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationUserService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final ConfirmationTokenService confirmationTokenService;
    private final DefaultEmailService defaultEmailService;


    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     */
    public void signUp(SignUpRequest request) {

        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();

        userService.create(user);
        var token = confirmationTokenService.generateToken(user);
        defaultEmailService.sendConfirmationEmail(user, token.getToken());
    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signIn(SignInRequest request) {

        if (!userService.getByUsername(request.getUsername()).isConfirmedEmail())
            throw new EmailNotConfirmException("Подтвердите почту");

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

    //todo Позже реализовать подтверждение через почту
    public JwtAuthenticationResponse setPassword(SetPasswordRequest passwordRequest) {
        var user = userService.getCurrentUser();
        user.setPassword(passwordEncoder.encode(passwordRequest.getPassword()));
        userService.saveUser(user);
        return new JwtAuthenticationResponse(jwtService.generateToken(user));
    }

    public JwtAuthenticationResponse updateUser(UpdateUserRequest userRequest) {
        return new JwtAuthenticationResponse(jwtService.generateToken(userService.updateUser(userRequest)));
    }

    public void confirmEmail(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token)
                .orElseThrow(() -> new TokenNotFoundException("Неверный токен"));

        if (!confirmationToken.isActive() || confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException("Токен истек");
        }

        confirmationTokenService.setConfirmedAt(confirmationToken);
        userService.confirmedEmail(confirmationToken.getUser().getUsername());
        confirmationTokenService.disableToken(confirmationToken);
    }


}
