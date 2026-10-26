package com.theinngu32.common.exception;

public class ImageCompressionException extends RuntimeException {
    public ImageCompressionException(String message) {
        super(message);
    }

    public ImageCompressionException(String message, Throwable cause) {
        super(message, cause);
    }
}
