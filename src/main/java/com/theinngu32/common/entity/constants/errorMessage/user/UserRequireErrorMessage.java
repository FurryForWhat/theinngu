package com.theinngu32.common.entity.constants.errorMessage.user;

import com.theinngu32.common.entity.constants.errorMessage.ErrorMessageBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRequireErrorMessage implements ErrorMessageBaseEnum<String> {

    NAME("name", "အမည် မဖြည့်ထားပါ။"),
    EMAIL("email", "အီးမေးလ် မဖြည့်ထားပါ။"),
    PASSWORD("password", "လျှို့ဝှက်နံပါတ် မဖြည့်ထားပါ။"),
    ROLE("role", "အသုံးပြုသူ၏ အဆင့်ကို ရွေးချယ်ပါ။");

    private final String value;
    private final String errorMessage;
}
