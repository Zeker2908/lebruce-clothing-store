package ru.lebruce.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lebruce.store.domain.model.PasswordResetToken;
import ru.lebruce.store.domain.model.User;
import ru.lebruce.store.exception.TokenInvalidException;
import ru.lebruce.store.exception.TokenNotFoundException;
import ru.lebruce.store.repository.PasswordResetTokenRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenService {
    private final PasswordResetTokenRepository tokenRepository;

    public PasswordResetToken generateToken(User user) {
        String token = UUID.randomUUID().toString();
        PasswordResetToken myToken = PasswordResetToken.builder()
                .token(token)
                .user(user)
                .expiresAt(LocalDateTime.now().plusMinutes(30))
                .build();
        return tokenRepository.save(myToken);
    }

    public PasswordResetToken getToken(String token) {
        return tokenRepository.findByToken(token).orElseThrow(() ->
                new TokenNotFoundException("Токен не найден"));
    }

    public boolean existsByUser(User user) {
        return tokenRepository.existsByUser(user);
    }

    public boolean existsByToken(String token) {
        return tokenRepository.existsByToken(token);
    }

    public void expires(PasswordResetToken token) {
        if (token.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new TokenInvalidException("Срок действия токена истек");
        }
    }

    @Transactional
    public void delete(PasswordResetToken token) {
        getToken(token.getToken());
        tokenRepository.delete(token);
    }

}
