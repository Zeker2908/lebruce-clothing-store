package ru.lebruce.store.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<ShoppingCart> getShoppingCart() {
        return ResponseEntity.ok(shoppingCartService.getCurrentShoppingCart());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/quantity")
    public ResponseEntity<Integer> getQuantity() {
        return ResponseEntity.ok(shoppingCartService.getQuantityPosition());
    }

    @PostMapping("/item")
    public ResponseEntity<?> createShoppingCartItem(@RequestBody @Valid ShoppingCartItemRequest shoppingCartItemRequest) {
        shoppingCartItemService.create(shoppingCartItemRequest);
        return ResponseEntity.ok("Товар успешно добавлен в корзину");
    }

    @PutMapping("/item/{id}/{quantity}")
    public ResponseEntity<?> updateQuantity(@PathVariable Long id, @PathVariable int quantity) {
        shoppingCartItemService.updateQuantity(id, quantity);
        return ResponseEntity.ok("Товар успешно изменен");
    }


    @DeleteMapping("item/{id}")
    public ResponseEntity<?> deleteShoppingCartItem(@PathVariable Long id) {
        shoppingCartItemService.delete(id);
        return ResponseEntity.ok("Товар успешно удален");
    }
}
