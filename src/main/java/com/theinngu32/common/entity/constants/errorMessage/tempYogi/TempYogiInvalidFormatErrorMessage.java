package com.theinngu32.common.entity.constants.errorMessage.tempYogi;

import com.theinngu32.common.entity.constants.errorMessage.Regexable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.regex.Pattern;

@Getter
@AllArgsConstructor
public enum TempYogiInvalidFormatErrorMessage implements Regexable {
    PHONE("phone", "ထည့်သွင်းသည့် ဖုန်းနံံပါတ်ပုံစံ မှားယွင်းနေပါသည်။", "^\\d{7,15}$"),
    EMERGENCY_CONTACT("emergencyContact", "အရေးပေါ်ဆက်သွယ်ရန် ဖုန်းနံပါတ်ပုံစံ မှားယွင်းနေပါသည်။", "^\\d{7,15}$"),
    NRC_PREFIX_CODE("nrcPrefixCode", "မှတ်ပုံတင် အမှတ် အရှေ့ တိုင်းဒေသကြီး/ပြည်နယ် အမှတ်စဉ် မှားယွင်းနေပါသည်။", "^[0-9]{1,2}$"),
    NRC_DIVISION("nrcDivision", "မှတ်ပုံတင်အမှတ် မြို့နယ်အက္ခရာ မှားယွင်းနေပါသည်။", "^[A-Za-z]+$"),
    NRC_TYPE("nrcType", "မှတ်ပုံတင် အမျိုးအစား မှားယွင်းနေပါသည်။", "^(?:[A-Za-z]+|နိုင်|ဧည့်|ပြု|သ|ယာယီ|စ|သီ)$"),
    NRC_POST_FIX_DIGIT("nrcPostFixDigit", "မှတ်ပုံတင် နံပါတ် မှားယွင်းနေပါသည်။", "^\\d{6}$");

    private final String value;
    private final String errorMessage;
    private final String regex;

    @Override
    public Pattern getPattern() {
        return Pattern.compile(regex);
    }
}

