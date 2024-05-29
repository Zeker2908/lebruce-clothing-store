package ru.lebruce.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lebruce.store.domain.model.SetUsernameToken;
import ru.lebruce.store.domain.model.User;
import ru.lebruce.store.exception.TokenInvalidException;
import ru.lebruce.store.exception.TokenNotFoundException;
import ru.lebruce.store.repository.SetUsernameTokenRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SetUsernameTokenService {
    private final SetUsernameTokenRepository repository;

    public SetUsernameToken generateToken(User user, String username) {
        String token = UUID.randomUUID().toString();
        SetUsernameToken myToken = SetUsernameToken.builder()
                .token(token)
                .user(user)
                .username(username)
                .expiresAt(LocalDateTime.now().plusMinutes(30))
                .build();
        return repository.save(myToken);
    }

    public void expires(SetUsernameToken token) {
        if (token.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new TokenInvalidException("Срок действия токена истек");
        }
    }

    public SetUsernameToken getToken(String token) {
        return repository.findByToken(token).orElseThrow(() ->
                new TokenNotFoundException("Токен не найден"));
    }

    public boolean existsByToken(String token) {
        return repository.existsByToken(token);
    }

    public boolean existsByUser(User user) {
        return repository.existsByUser(user);
    }

    @Transactional
    public void delete(SetUsernameToken token) {
        repository.delete(token);
    }

}
