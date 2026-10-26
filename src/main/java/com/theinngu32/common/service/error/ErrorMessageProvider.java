package com.theinngu32.common.service.error;

import com.theinngu32.common.entity.constants.errorMessage.ErrorMessageBaseEnum;
import com.theinngu32.common.entity.constants.errorMessage.ErrorType;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

public class ErrorMessageProvider {
    private final Map<ErrorType, Class<? extends ErrorMessageBaseEnum<String>>> errorMessageClasses = new EnumMap<>(ErrorType.class);
    
    public ErrorMessageProvider registerErrorMessageClass(ErrorType errorType, Class<? extends ErrorMessageBaseEnum<String>> messageClass) {
        errorMessageClasses.put(errorType, messageClass);
        return this; // For method chaining
    }
    
    public Optional<Class<? extends ErrorMessageBaseEnum<String>>> getErrorMessageClass(ErrorType errorType) {
        return Optional.ofNullable(errorMessageClasses.get(errorType));
    }
}