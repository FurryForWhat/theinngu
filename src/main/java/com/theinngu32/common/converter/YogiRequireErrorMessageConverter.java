package com.theinngu32.common.converter;
import com.theinngu32.common.entity.constants.errorMessage.yogi.YogiRequireErrorMessage;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class YogiRequireErrorMessageConverter extends BaseEnumConverter<YogiRequireErrorMessage, String>{
    public YogiRequireErrorMessageConverter() {
        super(YogiRequireErrorMessage.class);
    }
}