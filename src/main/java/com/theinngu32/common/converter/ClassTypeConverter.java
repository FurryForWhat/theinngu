package com.theinngu32.common.converter;

import com.theinngu32.common.entity.constants.ClassType;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ClassTypeConverter extends BaseEnumConverter<ClassType, Integer> {
    public ClassTypeConverter() {
        super(ClassType.class);
    }
}
