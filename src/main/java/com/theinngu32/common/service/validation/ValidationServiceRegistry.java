package com.theinngu32.common.service.validation;

import java.util.Map;

public interface ValidationServiceRegistry {
    Map<Class<?>, BaseValidationService<?>> getValidationServices();
}
