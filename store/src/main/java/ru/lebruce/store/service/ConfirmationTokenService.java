package ru.lebruce.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lebruce.store.domain.model.ConfirmationToken;
import ru.lebruce.store.domain.model.PendingUser;
import ru.lebruce.store.exception.TokenNotFoundException;
import ru.lebruce.store.repository.ConfirmationTokenRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ConfirmationTokenService {
    private static final int TOKEN_LENGTH = 32;

    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final UserService userService;

    public ConfirmationToken saveConfirmationToken(ConfirmationToken token) {
        return confirmationTokenRepository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }


    public ConfirmationToken generateToken(PendingUser user) {
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = ConfirmationToken.builder()
                .token(token)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(5))
                .user(user)
                .build();
        return saveConfirmationToken(confirmationToken);
    }

    public void deleteToken(String token) {
        confirmationTokenRepository.findByToken(token).orElseThrow(() ->
                new TokenNotFoundException("Токен не найден"));
        confirmationTokenRepository.deleteByToken(token);
    }

}
