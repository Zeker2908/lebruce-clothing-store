package ru.lebruce.store.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lebruce.store.domain.model.ShoppingCart;
import ru.lebruce.store.repository.ShoppingCartRepository;
import ru.lebruce.store.service.ShoppingCartService;
import ru.lebruce.store.service.UserService;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository repository;
    private final UserService userService;

    @Override
    public ShoppingCart getCurrentShoppingCart() {
        return repository.findByUser(userService.getCurrentUser());
    }

    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        return repository.save(shoppingCart);
    }


    @Override
    public ShoppingCart update(ShoppingCart shoppingCart) {
        return repository.save(shoppingCart);
    }

    @Override
    @Transactional
    public void delete(ShoppingCart shoppingCart) {
        repository.delete(shoppingCart);
    }
}
