package ru.lebruce.store.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lebruce.store.domain.model.ShoppingCart;
import ru.lebruce.store.exception.ShoppingCartNotFoundException;
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
        return repository.findByUser(userService.getCurrentUser()).orElseThrow(() ->
                new ShoppingCartNotFoundException("Корзина не найдена"));
    }

    @Override
    public Integer getQuantityPosition() {
        ShoppingCart shoppingCart = getCurrentShoppingCart();
        return shoppingCart.getQuantityOfPosition();
    }

    @Override
    public void cleanShoppingCart() {
        ShoppingCart shoppingCart = getCurrentShoppingCart();
        shoppingCart.getItems().clear();
        save(shoppingCart);
    }

    @Override
    public ShoppingCart save(ShoppingCart shoppingCart) {
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
