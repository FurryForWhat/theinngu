package com.theinngu32.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception to handle Bad Request scenarios.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST) // Automatically returns 400 BAD_REQUEST status
public class BadRequestException extends RuntimeException {
    private String message;

    // Constructor to pass a message
    public BadRequestException(String message) {
        super(message);
        this.message = message;
    }

    // Optional: You can override the `getMessage` method for a custom message formatting.
    @Override
    public String getMessage() {
        return this.message != null ? this.message : super.getMessage();
    }
}
