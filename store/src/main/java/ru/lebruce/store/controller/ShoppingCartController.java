package ru.lebruce.store.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.lebruce.store.domain.dto.ShoppingCartItemRequest;
import ru.lebruce.store.domain.model.ShoppingCart;
import ru.lebruce.store.service.ShoppingCartItemService;
import ru.lebruce.store.service.ShoppingCartService;

@RestController
@RequestMapping("api/v1/cart")
@RequiredArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final ShoppingCartItemService shoppingCartItemService;

    @GetMapping
    public ShoppingCart getShoppingCart() {
        return shoppingCartService.getCurrentShoppingCart();
    }

    @PostMapping("/item")
    public void createShoppingCartItem(@RequestBody @Valid ShoppingCartItemRequest shoppingCartItemRequest) {
        shoppingCartItemService.create(shoppingCartItemRequest);
    }

    @PutMapping("/item/{id}/{quantity}")
    public void updateQuantity(@PathVariable Long id, @PathVariable int quantity) {
        shoppingCartItemService.updateQuantity(id, quantity);
    }


    @DeleteMapping("item/{id}")
    public void deleteShoppingCartItem(@PathVariable Long id) {
        shoppingCartItemService.delete(id);
    }
}
