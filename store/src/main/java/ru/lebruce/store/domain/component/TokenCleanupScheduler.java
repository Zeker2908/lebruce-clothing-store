package ru.lebruce.store.domain.component;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.lebruce.store.domain.model.ConfirmationToken;
import ru.lebruce.store.domain.model.PasswordResetToken;
import ru.lebruce.store.repository.ConfirmationTokenRepository;
import ru.lebruce.store.repository.PasswordResetTokenRepository;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TokenCleanupScheduler {
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Scheduled(fixedRate = 300000) //каждые 5 минут
    public void cleanUpExpiredConfirmTokens() {
        List<ConfirmationToken> expiredTokens = confirmationTokenRepository
                .findAllByExpiresAtBefore(LocalDateTime.now());
        confirmationTokenRepository.deleteAll(expiredTokens);
    }

    @Scheduled(fixedRate = 1800000) //каждые 30 минут
    public void cleanUpExpiredPasswordTokens() {
        List<PasswordResetToken> expiredTokens = passwordResetTokenRepository
                .findAllByExpiresAtBefore(LocalDateTime.now());
        passwordResetTokenRepository.deleteAll(expiredTokens);
    }
}
