package ru.lebruce.store.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lebruce.store.domain.dto.ShoppingCartItemRequest;
import ru.lebruce.store.domain.model.Product;
import ru.lebruce.store.domain.model.ProductSize;
import ru.lebruce.store.domain.model.ShoppingCartItem;
import ru.lebruce.store.domain.model.User;
import ru.lebruce.store.exception.ShoppingCartItemAlreadyExists;
import ru.lebruce.store.exception.ShoppingCartItemNotFoundException;
import ru.lebruce.store.repository.ShoppingCartItemRepository;
import ru.lebruce.store.service.ProductService;
import ru.lebruce.store.service.ProductSizeService;
import ru.lebruce.store.service.ShoppingCartItemService;
import ru.lebruce.store.service.UserService;

@Service
@RequiredArgsConstructor
public class ShoppingCartItemServiceImpl implements ShoppingCartItemService {
    private final ShoppingCartItemRepository repository;
    private final UserService userService;
    private final ProductService productService;
    private final ProductSizeService productSizeService;

    @Override
    public ShoppingCartItem create(ShoppingCartItemRequest shoppingCartItemRequest) {
        User user = userService.getCurrentUser();
        Product product = productService.getByProductId(shoppingCartItemRequest.getProductId());
        ProductSize size = productSizeService.getProductSizeById(shoppingCartItemRequest.getSizeId());

        if (!size.getAvailable()) {
            throw new IllegalArgumentException("Данный размер закончился");
        }

        if (repository.existsByProductAndShoppingCartAndSize(product, user.getShoppingCart(), size)) {
            throw new ShoppingCartItemAlreadyExists("Товар уже добавлен в корзину");
        }

        var item = ShoppingCartItem.builder()
                .shoppingCart(user.getShoppingCart())
                .product(product)
                .quantity(shoppingCartItemRequest.getQuantity())
                .size(size)
                .build();

        return repository.save(item);
    }


    @Override
    @Transactional
    public void delete(Long shoppingCartItemId) {
        if (!repository.existsById(shoppingCartItemId)) {
            throw new ShoppingCartItemNotFoundException("Такого товара не существует");
        }
        repository.deleteByShoppingCartItemId(shoppingCartItemId);
    }

    @Override
    public ShoppingCartItem updateQuantity(Long shoppingCartItemId, int quantityChange) {
        var item = repository.findById(shoppingCartItemId)
                .orElseThrow(() -> new ShoppingCartItemNotFoundException("Объект не найден"));

        if (!item.getShoppingCart().equals(userService.getCurrentUser().getShoppingCart())) {
            throw new IllegalArgumentException("Элемент не принадлежит указанной корзине");
        }

        int newQuantity = Math.max(item.getQuantity() + quantityChange, 0);

        if (newQuantity == 0) {
            repository.deleteById(shoppingCartItemId);
            return null;
        }

        if (newQuantity > item.getSize().getQuantity()) {
            throw new IllegalArgumentException("Превышает доступное количество продукции");
        }

        item.setQuantity(newQuantity);
        return repository.save(item);
    }


}

