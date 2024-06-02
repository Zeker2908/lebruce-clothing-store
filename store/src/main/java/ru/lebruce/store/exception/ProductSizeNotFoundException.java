package ru.lebruce.store.exception;

public class ProductSizeNotFoundException extends RuntimeException {
    public ProductSizeNotFoundException(String message) {
        super(message);
    }
}
