package com.theinngu32.common.entity.constants.errorMessage.user;

import com.theinngu32.common.entity.constants.errorMessage.Regexable;
import com.theinngu32.common.utils.constants.RegexUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.regex.Pattern;

@Getter
@AllArgsConstructor
public enum UserInvalidFormatErrorMessage implements Regexable {

    EMAIL("email", "အီးမေးလ် ပုံစံမမှန်ပါ။", RegexUtils.EMAIL_REGEX),
    NAME("name", "နာမည်တွင် ဂဏန်းများမပါရ၊ ပုံစံမမှန်ပါ။ ကျေးဇူးပြု၍ အမည်အရှည်ကို ၁ မှ ၁၀၀ အတွင်း ဖြစ်ပါရန်။",RegexUtils.NAME_REGEX ),
    PASSWORD("password", "လျှို့ဝှက်စကားဝှက်၌ အနည်းဆုံး တစ်ခုစီသော စာလုံးအကြီး၊ စာလုံးအသေး၊ ဂဏန်း၊ အထူးသင်္ကေတပါဝင်ရမည်။ အရှည်မှာ ၈ မှ ၂၀ အတွင်း ဖြစ်ရပါမည်။",RegexUtils.PASSWORD_REGEX);

    private final String value;
    private final String errorMessage;
    private final String regex;
    @Override
    public Pattern getPattern() {
        return Pattern.compile(regex);
    }
}
