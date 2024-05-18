package ru.lebruce.store.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lebruce.store.domain.model.PendingUser;
import ru.lebruce.store.exception.PendingUserNotFoundException;
import ru.lebruce.store.repository.PendingUserRepository;
import ru.lebruce.store.service.PendingUserService;

@Service
@RequiredArgsConstructor
public class PendingUserServiceImpl implements PendingUserService {
    private final PendingUserRepository pendingUserRepository;

    @Override
    public boolean existsByUsername(String username) {
        return pendingUserRepository.existsByUsername(username);
    }

    @Override
    public void create(PendingUser pendingUser) {
        if (pendingUserRepository.existsByUsername(pendingUser.getUsername())) {
            throw new PendingUserNotFoundException("Временный пользователь уже существует");
        }
        pendingUserRepository.save(pendingUser);
    }

    @Override
    public PendingUser findByUsername(String username) {
        return pendingUserRepository.findByUsername(username).orElseThrow(() ->
                new PendingUserNotFoundException("Пользователь не найден"));
    }
}
