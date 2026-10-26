package com.theinngu32.common.converter;
import com.theinngu32.common.entity.constants.errorMessage.ErrorType;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ErrorTypeConverter extends BaseEnumConverter<ErrorType, Integer> {

    public ErrorTypeConverter() {
        super(ErrorType.class);
    }
}

