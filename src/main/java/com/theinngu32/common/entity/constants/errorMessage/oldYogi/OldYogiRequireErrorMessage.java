package com.theinngu32.common.entity.constants.errorMessage.oldYogi;

import com.theinngu32.common.entity.constants.errorMessage.ErrorMessageBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OldYogiRequireErrorMessage implements ErrorMessageBaseEnum<String> {
    NAME("name", "အမည် မဖြည့်ထားပါ။"),
    FATHER_NAME("fatherName", "အဖအမည် မဖြည့်ထားပါ။"),
    GENDER_TYPE("genderType", "ယောဂီအမျိုးအစား မဖြည့်ထားပါ။"),
    AGE("age", "အသက် မဖြည့်ထားပါ။"),
    ADDRESS("address", "နေရပ်လိပ်စာ မဖြည့်ထားပါ။");

    private final String value;
    private final String errorMessage;

}
