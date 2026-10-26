package com.theinngu32.common.entity.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Rating implements BaseEnum<Integer>{
    EXCELLENT(1),
    GOOD(2),
    AVERAGE(3),
    BELOW_AVERAGE(4),
    POOR(5);
    private final int value;

    @Override
    public Integer getValue() {
        return this.value;
    }
}
