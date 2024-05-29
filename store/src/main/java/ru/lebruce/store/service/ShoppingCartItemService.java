package ru.lebruce.store.service;

import jakarta.transaction.Transactional;
import ru.lebruce.store.domain.dto.ShoppingCartItemRequest;
import ru.lebruce.store.domain.model.ShoppingCartItem;

public interface ShoppingCartItemService {

    ShoppingCartItem create(ShoppingCartItemRequest shoppingCartItem);

    @Transactional
    void delete(Long shoppingCartItemId);

    ShoppingCartItem updateQuantity(Long shoppingCartItemId, int quantityChange);
}
