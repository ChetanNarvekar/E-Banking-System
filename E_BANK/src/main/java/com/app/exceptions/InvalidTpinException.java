package com.app.exceptions;

public class InvalidTpinException extends RuntimeException {
    public InvalidTpinException(String message) {
        super(message);
    }
}