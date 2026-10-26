package com.theinngu32.common.exception;

public class FileUploadFailedException extends RuntimeException {

    public FileUploadFailedException(String message) {
        super(message);
    }

    public FileUploadFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}

