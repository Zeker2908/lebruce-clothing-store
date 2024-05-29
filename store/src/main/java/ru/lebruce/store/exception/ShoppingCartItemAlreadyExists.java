package ru.lebruce.store.exception;

public class ShoppingCartItemAlreadyExists extends RuntimeException {
    public ShoppingCartItemAlreadyExists(String message) {
        super(message);
    }
}
