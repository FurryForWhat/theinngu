package com.theinngu32.common.entity.constants.errorMessage.meditationClass;

import com.theinngu32.common.entity.constants.errorMessage.ErrorMessageBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MeditationClassRequireErrorMessage implements ErrorMessageBaseEnum<String> {

    TITLE("title","တရားစခန်းအမည် မဖြည့်ထားပါ။"),
    START_DATE("startDate","တရားစခန်းစတင်ဖွင့်မည့်အချိန် မဖြည့်ထားပါ။"),
    END_DATE("endDate","တရားစခန်းပြီးဆုံးမည့်အချိန် မဖြည့်ထားပါ။"),
    CLASS_TYPE("classType","တရားစခန်းအမျိုးအစား မဖြည့်ထားပါ။"),
    LEVEL("level","တရားစခန်း ကျင့်စဥ်အမျိုးအစား မဖြည့်ထားပါ။");

    private final String value;
    private final String errorMessage;
}
