package com.theinngu32.common.service.error;

import com.theinngu32.common.dto.ErrorDto;
import com.theinngu32.common.entity.CommonError;
import com.theinngu32.common.entity.MasterData;
import com.theinngu32.common.entity.constants.errorMessage.ErrorMessageBaseEnum;
import com.theinngu32.common.repository.ValidationErrorRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public abstract class BaseErrorService<T extends MasterData> {

    private final ValidationErrorRepository validationErrorRepository;
    private final ErrorMessageProviderFactory<T> errorMessageProviderFactory;

    @PostConstruct
    public abstract void registerErrorMessageProviders();

    public List<ErrorDto> getErrors(Long recordId, String entityName) {
        return validationErrorRepository
                .findByEntityNameAndRecordId(entityName, recordId)
                .orElse(List.of())
                .stream()
                .map(this::mapToErrorDto)
                .toList();
    }

    private ErrorDto mapToErrorDto(CommonError error) {
        String errorMessage = "";

        Optional<ErrorMessageProvider> providerOpt = errorMessageProviderFactory
                .getProviderByName(error.getEntityName());

        if (providerOpt.isPresent()) {
            ErrorMessageProvider provider = providerOpt.get();
            Optional<Class<? extends ErrorMessageBaseEnum<String>>> errorMessageClassOpt =
                    provider.getErrorMessageClass(error.getErrorType());

            if (errorMessageClassOpt.isPresent()) {
                errorMessage = getErrorMessage(errorMessageClassOpt.get(), error.getFieldName());
            }
        }

        return ErrorDto.builder()
                .errorType(error.getErrorType())
                .fieldName(error.getFieldName())
                .invalidValue(error.getInvalidValue())
                .errorMessage(errorMessage)
                .build();
    }

    protected String convertToEnumFormat(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }
        return input.replaceAll("([a-z])([A-Z])", "$1_$2").toUpperCase();
    }

    @SuppressWarnings("unchecked")
    protected String getErrorMessage(Class<? extends ErrorMessageBaseEnum<String>> enumClass, String messageKey) {
        if (enumClass == null || messageKey == null) {
            return "";
        }
        try {
            String enumKey = convertToEnumFormat(messageKey);
            Method valueOf = enumClass.getMethod("valueOf", String.class);
            ErrorMessageBaseEnum<String> enumValue = (ErrorMessageBaseEnum<String>) valueOf.invoke(null, enumKey);
            return Optional.ofNullable(enumValue.getErrorMessage()).orElse("");
        } catch (Exception e) {
            return "";
        }
    }
}