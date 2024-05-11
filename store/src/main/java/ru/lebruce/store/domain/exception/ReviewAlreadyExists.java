package ru.lebruce.store.domain.exception;

public class ReviewAlreadyExists extends RuntimeException {
    public ReviewAlreadyExists(String message) {
        super(message);
    }
}
