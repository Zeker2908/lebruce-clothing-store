package ru.lebruce.store.service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.lebruce.store.domain.dto.*;
import ru.lebruce.store.domain.model.PendingUser;
import ru.lebruce.store.exception.EmailNotConfirmException;

@Service
@RequiredArgsConstructor
public class AuthenticationUserService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailService emailService;
    private final PendingUserService pendingUserService;

    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     */
    @Async("taskExecutor")
    public void signUp(SignUpRequest request) throws MessagingException {

        var pendingUser = PendingUser.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();


        pendingUserService.create(pendingUser);
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

        if (pendingUserService.existsByUsername(request.getUsername())) {
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

}
