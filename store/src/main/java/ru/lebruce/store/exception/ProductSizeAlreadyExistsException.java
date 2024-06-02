package ru.lebruce.store.exception;

public class ProductSizeAlreadyExistsException extends RuntimeException {
    public ProductSizeAlreadyExistsException(String message) {
        super(message);
    }
}
