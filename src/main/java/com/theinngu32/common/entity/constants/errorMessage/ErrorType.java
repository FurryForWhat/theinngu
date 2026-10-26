package com.theinngu32.common.entity.constants.errorMessage;

import com.theinngu32.common.entity.constants.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorType implements BaseEnum<Integer> {
    REQUIRED_ERROR(0),
    INVALID_FORMAT_ERROR(1),
    DATA_INTEGRITY_ERROR(2),
    DUPLICATE_ERROR(3),
    BLACKLIST_ERROR(4),
    INVALID_VALUE_ERROR(5);

    private final int value;

    @Override
    public Integer getValue() {
        return this.value;
    }
}