package ru.lebruce.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lebruce.store.domain.model.Role;
import ru.lebruce.store.domain.model.User;
import ru.lebruce.store.exception.TokenExpiredException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ConfirmationEmailService {
    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;
    private final PendingUserService pendingUserService;

    @Transactional
    public void confirmEmail(String token) {
        var confirmationToken = confirmationTokenService.getToken(token);
        if (confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException("Токен истек");
        }
        var pendingUser = pendingUserService.findByUsername(confirmationToken.getUser().getUsername());

        var user = User.builder()
                .username(pendingUser.getUsername())
                .password(pendingUser.getPassword())
                .role(Role.ROLE_USER)
                .firstName(pendingUser.getFirstName())
                .lastName(pendingUser.getLastName())
                .build();

        userService.create(user);
        confirmationTokenService.delete(token);
    }

}
