package ru.lebruce.store.exception;

public class BrandAlreadyExistsException extends RuntimeException {
    public BrandAlreadyExistsException(String message) {
        super(message);
    }
}
