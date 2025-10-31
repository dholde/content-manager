package com.dehold.contentmanager.exception;

public class InvalidPayloadException extends RuntimeException {

    public InvalidPayloadException(String message) {
        super(message);
    }

    public static InvalidPayloadException of(String details) {
        return new InvalidPayloadException(String.format("Invalid payload: %s", details));
    }
}
