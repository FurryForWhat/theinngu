package com.theinngu32.common.exception;

public class UnsupportedFileException extends RuntimeException {
    public UnsupportedFileException(String message) {
        super(message);
    }
    public UnsupportedFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
