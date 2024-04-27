package ru.lebruce.store.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lebruce.store.model.User;
import ru.lebruce.store.repository.UserRepository;
import ru.lebruce.store.service.UserService;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public List<User> findAllUsers() {
        return repository.findAll();
    }

    @Override
    public User saveUser(User user) {
        return repository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public User updateUser(User user) {
        return repository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        repository.deleteByUserId(userId);
    }

    @Override
    @Transactional
    public void deleteUser(String email) {
        repository.deleteByEmail(email);
    }
}
