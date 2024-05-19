package ru.lebruce.store.service;

import ru.lebruce.store.domain.model.PendingUser;

public interface PendingUserService {
    boolean existsByUsername(String username);

    void create(PendingUser pendingUser);

    PendingUser findByUsername(String username);

    void checkUserExistence(String username);
}
