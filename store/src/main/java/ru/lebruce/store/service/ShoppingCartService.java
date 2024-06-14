package ru.lebruce.store.service;

import ru.lebruce.store.domain.model.ShoppingCart;

public interface ShoppingCartService {
    ShoppingCart getCurrentShoppingCart();

    Integer getQuantityPosition();

    void cleanShoppingCart();

    ShoppingCart save(ShoppingCart shoppingCart);

    ShoppingCart update(ShoppingCart shoppingCart);

    void delete(ShoppingCart shoppingCart);

}
