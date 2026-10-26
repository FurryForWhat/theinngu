package com.theinngu32.common.converter;
import com.theinngu32.common.entity.constants.NrcType;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class NrcTypeConverter extends BaseEnumConverter<NrcType, String> {

    public NrcTypeConverter() {
        super(NrcType.class);
    }
}
