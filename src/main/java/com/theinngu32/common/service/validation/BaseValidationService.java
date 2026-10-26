package com.theinngu32.common.service.validation;

import com.theinngu32.common.entity.CommonError;
import com.theinngu32.common.entity.MasterData;
import com.theinngu32.common.entity.constants.errorMessage.ErrorType;
import com.theinngu32.common.repository.ValidationErrorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public abstract class BaseValidationService<T> implements ValidationDetails<T> {

    protected final ValidationErrorRepository validationErrorRepository;
    protected T object;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void validateAndLog(T object) {
        this.object = object;
        validate();
        validationErrorRepository.flush();
    }

    protected void validate() {
        Class<?> currentClass = object.getClass();
        while (currentClass != null && !currentClass.equals(Object.class)) {
            for (Field field : currentClass.getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    validateNotEmpty(field);
                    validateFormat(field);
                    validateDataIntegrity(field);
                    validateUnique(field);
                    validateBlacklist(field);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Error accessing field: " + field.getName(), e);
                }
            }
            currentClass = currentClass.getSuperclass();
        }
    }

    private void validateNotEmpty(Field field) throws IllegalAccessException {
        if (getRequiredFields().contains(field.getName())) {
            Object value = field.get(object);
            if (value == null || (value instanceof String && ((String) value).trim().isEmpty())) {
                saveErrorMessage(field.getName(), ErrorType.REQUIRED_ERROR, null);
            } else {
                clearErrorMessage(field.getName(), ErrorType.REQUIRED_ERROR);
            }
        }
    }

    private void validateFormat(Field field) throws IllegalAccessException {
        var fieldName = field.getName();
        if (getFormatFields().containsKey(fieldName)) {
            Object value = field.get(object);
            if (value instanceof String stringValue) {
                if (!getFormatFields().get(fieldName).matcher(stringValue).matches()) {
                    saveErrorMessage(field.getName(), ErrorType.INVALID_FORMAT_ERROR, stringValue);
                } else {
                    clearErrorMessage(field.getName(), ErrorType.INVALID_FORMAT_ERROR);
                }
            }
        }
    }

    private void validateUnique(Field field) throws IllegalAccessException {
        var fieldName = field.getName();
        if (getUniqueFields().containsKey(fieldName)) {
            Object value = field.get(object);
            var duplicateChecker = getUniqueFields().get(fieldName);
            if (duplicateChecker.test(fieldName, object)) {
                saveErrorMessage(field.getName(), ErrorType.DUPLICATE_ERROR, value.toString());
            } else {
                clearErrorMessage(field.getName(), ErrorType.DUPLICATE_ERROR);
            }
        }
    }

    private void validateBlacklist(Field field) throws IllegalAccessException {
        var fieldName = field.getName();
        if (getBlacklistedFields().containsKey(fieldName)) {
            Object value = field.get(object);
            var blacklistChecker = getBlacklistedFields().get(fieldName);
            if (blacklistChecker.test(object)) {
                saveErrorMessage(field.getName(), ErrorType.BLACKLIST_ERROR, value.toString());
            } else {
                clearErrorMessage(field.getName(), ErrorType.BLACKLIST_ERROR);
            }

        }
    }

    private void validateDataIntegrity(Field field) throws IllegalAccessException {
        var integrityChecks = getIntegrityCheckFields();
        if (integrityChecks.containsKey(field.getName())) {
            Object value = field.get(object);
            var integrityChecker = integrityChecks.get(field.getName());
            if (value != null && !integrityChecker.test(object)) {
                saveErrorMessage(field.getName(), ErrorType.DATA_INTEGRITY_ERROR, value.toString());
            } else {
                clearErrorMessage(field.getName(), ErrorType.DATA_INTEGRITY_ERROR);
            }
        }
    }

    protected void clearErrorMessage(String fieldName, ErrorType errorType) {
        Long recordId = getEntityId();
        String className = object.getClass().getSimpleName();
        validationErrorRepository
                .findByEntityNameAndFieldNameAndRecordIdAndErrorType(className, fieldName, recordId, errorType)
                .ifPresent(validationErrorRepository::delete);
    }

    protected Long getEntityId() {
        try {
            MasterData masterData = (MasterData) object;
            return masterData.getId();
        } catch (Exception e) {
            return null;
        }
    }

    protected void saveErrorMessage(String fieldName, ErrorType errorType, String value) {
        Long recordId = getEntityId();
        String className = object.getClass().getSimpleName();
        Optional<CommonError> existingSameError = validationErrorRepository
                .findByEntityNameAndFieldNameAndRecordIdAndErrorType(className, fieldName, recordId, errorType);

        if (existingSameError.isPresent()) {
            CommonError error = existingSameError.get();
            error.setInvalidValue(value);
            validationErrorRepository.save(error);
        } else {
            CommonError newError = new CommonError(className, fieldName, recordId);
            newError.setInvalidValue(value);
            newError.setErrorType(errorType);
            validationErrorRepository.save(newError);
        }
    }
}
