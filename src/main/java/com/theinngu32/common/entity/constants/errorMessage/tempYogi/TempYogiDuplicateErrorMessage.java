package com.theinngu32.common.entity.constants.errorMessage.tempYogi;

import com.theinngu32.common.entity.TempYogi;
import com.theinngu32.common.entity.constants.errorMessage.DuplicateValidationRule;
import com.theinngu32.common.utils.validation.duplicate.TempYogiDuplicateCheckerUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.BiPredicate;

@Getter
@AllArgsConstructor
public enum TempYogiDuplicateErrorMessage implements DuplicateValidationRule<TempYogi> {

    PASSPORT("passport", "ယောဂီ၏ Passport သည် ကွန်ပျူတာစနစ်ထဲ၌ သိမ်းဆည်းထားပြီးဖြစ်ပါသည်။",
            TempYogiDuplicateCheckerUtils::isDuplicate
    ),
    YOGI_ID("yogiId", "ယောဂီ၏ ယောဂီအမှတ်စဥ်သည် ကွန်ပျူတာစနစ်ထဲ၌ သိမ်းဆည်းထားပြီးဖြစ်ပါသည်။",
            TempYogiDuplicateCheckerUtils::isDuplicate
    ),
    NRC("nrc","ယောဂီ၏ မှတ်ပုံတင်အမှတ်သည် ကွန်ပျူတာစနစ်ထဲ၌ သိမ်းဆည်းထားပြီးဖြစ်ပါသည်။",
            TempYogiDuplicateCheckerUtils::isDuplicate);

    // "ယောဂီ ၏ Passport သည် File ထဲတွင် သိမ်းထားပြီးဖြစ်ပါသည်။"
    // "ယောဂီ ၏ YogiId သည် File ထဲတွင် သိမ်းထားပြီးဖြစ်ပါသည်။"

    private final String value;
    private final String errorMessage;
    private final BiPredicate<String, TempYogi> duplicateRule;

}