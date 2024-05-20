package ru.lebruce.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lebruce.store.domain.model.ConfirmationToken;
import ru.lebruce.store.domain.model.PendingUser;
import ru.lebruce.store.exception.TokenNotFoundException;
import ru.lebruce.store.repository.ConfirmationTokenRepository;

import java.time.LocalDateTime;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;

    public ConfirmationToken saveConfirmationToken(ConfirmationToken token) {
        return confirmationTokenRepository.save(token);
    }

    public ConfirmationToken getToken(String token) {
        return confirmationTokenRepository.findByToken(token).orElseThrow(()
                -> new TokenNotFoundException("Токен недействителен"));
    }


    public ConfirmationToken generateToken(PendingUser user) {
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = ConfirmationToken.builder()
                .token(token)
                .expiresAt(LocalDateTime.now().plusMinutes(5))
                .user(user)
                .build();
        return saveConfirmationToken(confirmationToken);
    }

    @Transactional
    public void delete(String token) {
        getToken(token);
        confirmationTokenRepository.deleteByToken(token);
    }

}
