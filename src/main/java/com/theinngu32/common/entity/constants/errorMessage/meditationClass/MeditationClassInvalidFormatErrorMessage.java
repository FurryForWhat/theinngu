package com.theinngu32.common.entity.constants.errorMessage.meditationClass;

import com.theinngu32.common.entity.constants.errorMessage.Regexable;
import com.theinngu32.common.utils.constants.RegexUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.regex.Pattern;

@Getter
@AllArgsConstructor
public enum MeditationClassInvalidFormatErrorMessage implements Regexable {

    START_DATE("startDate","တရားစခန်းစတင်ဖွင့်မည့်ချိန် ပုံစံမမှန်ပါ။", RegexUtils.START_DATE_REGEX),
    END_DATE("endDate","တရားစခန်းပြီးဆုံးမည့်ချိန် ပုံစံမမှန်ပါ။",RegexUtils.END_DATE_REGEX),
    TITLE("title", "ခေါင်းစဉ်အရှည်အများဆုံးသည် စာလုံး ၂၀၀ အတွင်းဖြစ်ရမည်။", RegexUtils.TITLE_REGEX);

    private final String value;
    private final String errorMessage;
    private final String regax;

    @Override
    public Pattern getPattern() {
        return Pattern.compile(regax);
    }
}
