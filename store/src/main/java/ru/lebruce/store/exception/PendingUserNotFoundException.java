package ru.lebruce.store.exception;

public class PendingUserNotFoundException extends RuntimeException {
    public PendingUserNotFoundException(String message) {
        super(message);
    }
}
