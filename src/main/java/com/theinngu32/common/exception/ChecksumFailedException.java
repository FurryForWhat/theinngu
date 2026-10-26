package com.theinngu32.common.exception;

public class ChecksumFailedException extends RuntimeException {
    public ChecksumFailedException(String message) {
        super(message);
    }
    public ChecksumFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
