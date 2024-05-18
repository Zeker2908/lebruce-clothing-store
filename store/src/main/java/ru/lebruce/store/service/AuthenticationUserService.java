package ru.lebruce.store.service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lebruce.store.domain.dto.*;
import ru.lebruce.store.domain.model.PendingUser;
import ru.lebruce.store.domain.model.Role;
import ru.lebruce.store.domain.model.User;
import ru.lebruce.store.exception.EmailNotConfirmException;
import ru.lebruce.store.exception.TokenExpiredException;
import ru.lebruce.store.exception.TokenNotFoundException;
import ru.lebruce.store.exception.UserAlreadyExistsException;
import ru.lebruce.store.repository.PendingUserRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationUserService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailService emailService;
    private final PendingUserRepository pendingUserRepository;


    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     */
    public void signUp(SignUpRequest request) throws MessagingException {
        if (userService.existsByUsername(request.getUsername()) || pendingUserRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("Пользователь с почтой " + request.getUsername() + " уже существует");
        }

        var pendingUser = PendingUser.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();


        pendingUserRepository.save(pendingUser);
        var token = confirmationTokenService.generateToken(pendingUser);
        var emailContext = emailService.confirmEmailContext(pendingUser, token.getToken());
        emailService.sendConfirmationEmail(emailContext);
    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signIn(SignInRequest request) {

        if (pendingUserRepository.existsByUsername(request.getUsername())) {
            throw new EmailNotConfirmException("Подтвердите почту");
        }

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

    @Transactional
    public void confirmEmail(String token) {
        var confirmationToken = confirmationTokenService.getToken(token)
                .orElseThrow(() -> new TokenNotFoundException("Неверный токен"));
        if (confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException("Токен истек");
        } else if (userService.existsByUsername(confirmationToken.getUser().getUsername())) {
            throw new UserAlreadyExistsException("Пользователь уже создан");
        }
        var pendingUser = pendingUserRepository.findByUsername(confirmationToken.getUser().getUsername())
                .orElseThrow(() -> new RuntimeException("Временный пользователь не найден"));

        var user = User.builder()
                .username(pendingUser.getUsername())
                .password(pendingUser.getPassword())
                .role(Role.ROLE_USER)
                .firstName(pendingUser.getFirstName())
                .lastName(pendingUser.getLastName())
                .build();

        userService.create(user);
        confirmationTokenService.deleteToken(token);
    }


}
