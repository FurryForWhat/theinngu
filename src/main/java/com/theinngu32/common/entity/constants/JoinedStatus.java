package com.theinngu32.common.entity.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum JoinedStatus implements BaseEnum<Integer>{
    PASS(1),
    FAIL(2),
    CANCEL(3);

    private final int value;

    @Override
    public Integer getValue() {
        return this.value;
    }
}
