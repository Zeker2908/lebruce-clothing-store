package ru.lebruce.store.exception;

public class EmailNotConfirmException extends RuntimeException {
    public EmailNotConfirmException(String message) {
        super(message);
    }
}
