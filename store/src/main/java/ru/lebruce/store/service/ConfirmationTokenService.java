package ru.lebruce.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lebruce.store.domain.model.ConfirmationToken;
import ru.lebruce.store.domain.model.User;
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

    public void setConfirmedAt(ConfirmationToken token) {
        token.setConfirmedAt(LocalDateTime.now());
        saveConfirmationToken(token);
    }

    public void disableConfirmation(ConfirmationToken token) {
        token.setActive(false);
        saveConfirmationToken(token);
    }

    public ConfirmationToken generateToken(User user) {
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = ConfirmationToken.builder()
                .token(token)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .isActive(true)
                .build();
        return saveConfirmationToken(confirmationToken);
    }

}
