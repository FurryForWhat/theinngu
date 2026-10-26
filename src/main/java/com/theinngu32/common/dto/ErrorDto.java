package com.theinngu32.common.dto;

import com.theinngu32.common.entity.constants.errorMessage.ErrorType;
import lombok.Builder;

@Builder
public record ErrorDto(
        String errorMessage,
        ErrorType errorType,
        String fieldName,
        String invalidValue
) {}
