package ru.lebruce.store.exception;

public class ShoppingCartItemNotFoundException extends RuntimeException {
    public ShoppingCartItemNotFoundException(String message) {
        super(message);
    }
}
