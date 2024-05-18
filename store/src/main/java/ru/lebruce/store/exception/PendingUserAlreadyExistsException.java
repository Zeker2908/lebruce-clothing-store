package ru.lebruce.store.exception;

public class PendingUserAlreadyExistsException extends RuntimeException {
    public PendingUserAlreadyExistsException(String message) {
        super(message);
    }
}
