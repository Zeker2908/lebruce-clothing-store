package ru.lebruce.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.lebruce.store.domain.dto.JwtAuthenticationResponse;
import ru.lebruce.store.domain.dto.SetPasswordRequest;
import ru.lebruce.store.domain.dto.SignInRequest;
import ru.lebruce.store.domain.dto.SignUpRequest;
import ru.lebruce.store.domain.model.Role;
import ru.lebruce.store.domain.model.User;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signUp(SignUpRequest request) {

        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();

        userService.create(user);

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signIn(SignInRequest request) {
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
    public JwtAuthenticationResponse setPassword(SetPasswordRequest passwordRequest){
        var user = userService.getCurrentUser();
        user.setPassword(passwordEncoder.encode(passwordRequest.getPassword()));
        userService.saveUser(user);
        return new JwtAuthenticationResponse(jwtService.generateToken(user));
    }
}
