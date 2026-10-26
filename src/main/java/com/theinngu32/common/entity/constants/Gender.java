package com.theinngu32.common.entity.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum Gender implements BaseEnum<Integer>{
    MONK(1),
    NUN(2),
    NOVICE(3),
    MALE(4),
    FEMALE(5),
    OTHER(6);

    private final int value;

    @Override
    public Integer getValue() {
        return this.value;
    }

}
