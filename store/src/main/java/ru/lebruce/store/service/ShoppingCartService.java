package ru.lebruce.store.service;

import ru.lebruce.store.domain.model.ShoppingCart;

public interface ShoppingCartService {
    ShoppingCart getCurrentShoppingCart();

    Integer getQuantityPosition();

    ShoppingCart create(ShoppingCart shoppingCart);

    ShoppingCart update(ShoppingCart shoppingCart);

    void delete(ShoppingCart shoppingCart);

}
