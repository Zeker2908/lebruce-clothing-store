package ru.lebruce.store.service;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.lebruce.store.domain.dto.JwtAuthenticationResponse;
import ru.lebruce.store.domain.dto.SignInRequest;
import ru.lebruce.store.domain.dto.SignUpRequest;
import ru.lebruce.store.domain.model.PendingUser;
import ru.lebruce.store.exception.EmailNotConfirmException;
import ru.lebruce.store.exception.TokenInvalidException;

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
        emailService.sendEmail(emailContext);
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

        UserDetails user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

    public JwtAuthenticationResponse refreshToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring("Bearer ".length());
        String username = jwtService.extractUserName(token);
        if (username == null || !jwtService.isTokenValidUser(token, userService.userDetailsService().loadUserByUsername(username))) {
            throw new TokenInvalidException("Invalid token");
        }
        if (jwtService.isTokenExpired(token)) {
            token = jwtService.generateToken(userService.userDetailsService().loadUserByUsername(username));
        }
        return new JwtAuthenticationResponse(token);
    }


}