package com.theinngu32.common.converter;
import com.theinngu32.common.entity.constants.Gender;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class GenderConverter extends BaseEnumConverter<Gender, Integer> {

    public GenderConverter() {
        super(Gender.class);
    }
}
