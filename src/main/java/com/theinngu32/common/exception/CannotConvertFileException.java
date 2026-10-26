package com.theinngu32.common.exception;

public class CannotConvertFileException extends RuntimeException {
    public CannotConvertFileException(String message) {
        super(message);
    }
    public CannotConvertFileException(String message, Throwable cause) {
      super(message, cause);
    }
}
