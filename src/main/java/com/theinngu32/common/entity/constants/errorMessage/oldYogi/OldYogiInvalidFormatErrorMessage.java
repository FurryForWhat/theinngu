package com.theinngu32.common.entity.constants.errorMessage.oldYogi;

import com.theinngu32.common.entity.constants.errorMessage.Regexable;
import com.theinngu32.common.utils.constants.RegexUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.regex.Pattern;

@AllArgsConstructor
@Getter
public enum OldYogiInvalidFormatErrorMessage implements Regexable {
    AGE("age", "အသက် မှားယွင်းနေပါသည်။", RegexUtils.AGE_REGEX),
    NRC("nrc", "မှတ်ပုံတင် အမှတ်ပုံစံ မှားယွင်းနေပါသည်။", RegexUtils.NRC_REGEX);

    private final String value;
    private final String errorMessage;
    private final String regex;

    @Override
    public Pattern getPattern() {
        return Pattern.compile(regex);
    }
}