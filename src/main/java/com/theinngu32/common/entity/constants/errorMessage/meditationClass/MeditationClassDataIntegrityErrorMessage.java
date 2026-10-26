package com.theinngu32.common.entity.constants.errorMessage.meditationClass;

import com.theinngu32.common.entity.constants.errorMessage.ErrorMessageBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MeditationClassDataIntegrityErrorMessage implements ErrorMessageBaseEnum<String> {
    START_DATE("startDate","ရွေးချယ်ထားသည့်ရက် မှားယွင်းနေသည်။"),
    END_DATE("endDate","ရွေးချယ်ထားသည့်ရက် မှားယွင်းနေသည်။");

    private final String value;
    private final String errorMessage;
}
