package com.watches.exception;

public class TitleAlreadyExistsException extends RuntimeException {
    public TitleAlreadyExistsException() {
        super("Entity with given title already exists");
    }

    public TitleAlreadyExistsException(String message) {
        super(message);
    }
}
