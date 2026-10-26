package com.theinngu32.common.service.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidationServiceProvider {
    private final ValidationServiceRegistry validator;

    @SuppressWarnings("unchecked")
    public <T> void validate(T entity) {
        Class<?> entityClass = entity.getClass();
        BaseValidationService<T> service = (BaseValidationService<T>) validator.getValidationServices().get(entityClass);
        if (service == null) {
            return;
        }
        service.validateAndLog(entity);
    }
}