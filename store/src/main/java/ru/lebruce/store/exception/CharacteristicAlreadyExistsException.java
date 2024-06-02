package ru.lebruce.store.exception;

public class CharacteristicAlreadyExistsException extends RuntimeException {
    public CharacteristicAlreadyExistsException(String message) {
        super(message);
    }
}
