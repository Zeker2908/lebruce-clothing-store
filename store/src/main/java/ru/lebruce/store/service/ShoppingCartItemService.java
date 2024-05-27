package ru.lebruce.store.service;

import jakarta.transaction.Transactional;
import ru.lebruce.store.domain.dto.ShoppingCartItemRequest;
import ru.lebruce.store.domain.model.ShoppingCartItem;

public interface ShoppingCartItemService {

    ShoppingCartItem create(ShoppingCartItemRequest shoppingCartItem);

    ShoppingCartItem update(ShoppingCartItem shoppingCartItem);
    
    @Transactional
    void delete(Long shoppingCartItemId);
}
