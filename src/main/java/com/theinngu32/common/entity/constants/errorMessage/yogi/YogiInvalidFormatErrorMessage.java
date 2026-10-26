package com.theinngu32.common.entity.constants.errorMessage.yogi;

import com.theinngu32.common.entity.constants.errorMessage.Regexable;
import com.theinngu32.common.utils.constants.RegexUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.regex.Pattern;

@AllArgsConstructor
@Getter
public enum YogiInvalidFormatErrorMessage implements Regexable {
    PHONE("phone", "ဖုန်းနံပါတ် ပုံစံမမှန်ပါ။", RegexUtils.PHONE_REGEX),
    EMERGENCY_CONTACT("emergencyContact", "အရေးပေါ်ဆက်သွယ်ရမည့် ဖုန်းနံပါတ်သည် ပုံစံမမှန်ပါ။", RegexUtils.PHONE_REGEX);

    private final String value;
    private final String errorMessage;
    private final String regex;

    @Override
    public Pattern getPattern() {
        return Pattern.compile(regex);
    }
}