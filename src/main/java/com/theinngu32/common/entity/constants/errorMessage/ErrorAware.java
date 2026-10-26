package com.theinngu32.common.entity.constants.errorMessage;

import com.theinngu32.common.dto.ErrorDto;

import java.util.List;

public interface ErrorAware {
    void setErrors(List<ErrorDto> errors);
}
