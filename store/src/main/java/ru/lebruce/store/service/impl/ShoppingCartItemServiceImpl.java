package ru.lebruce.store.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lebruce.store.domain.dto.ShoppingCartItemRequest;
import ru.lebruce.store.domain.model.ShoppingCartItem;
import ru.lebruce.store.repository.ShoppingCartItemRepository;
import ru.lebruce.store.service.ProductService;
import ru.lebruce.store.service.ShoppingCartItemService;
import ru.lebruce.store.service.UserService;

@Service
@RequiredArgsConstructor
public class ShoppingCartItemServiceImpl implements ShoppingCartItemService {
    private final ShoppingCartItemRepository repository;
    private final UserService userService;
    private final ProductService productService;

    @Override
    public ShoppingCartItem create(ShoppingCartItemRequest shoppingCartItemRequest) {
        var item = ShoppingCartItem.builder()
                .shoppingCart(userService.getCurrentUser().getShoppingCart())
                .product(productService.getByProductId(shoppingCartItemRequest.getProductId()))
                .quantity(shoppingCartItemRequest.getQuantity())
                .build();
        return repository.save(item);
    }

    @Override
    public ShoppingCartItem update(ShoppingCartItem shoppingCartItem) {
        return repository.save(shoppingCartItem);
    }

    @Override
    @Transactional
    public void delete(Long shoppingCartItemId) {
        repository.deleteByShoppingCartItemId(shoppingCartItemId);
    }
}
