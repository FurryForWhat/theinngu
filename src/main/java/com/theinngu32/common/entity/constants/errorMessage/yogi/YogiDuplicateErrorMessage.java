package com.theinngu32.common.entity.constants.errorMessage.yogi;

import com.theinngu32.common.entity.OldYogi;
import com.theinngu32.common.entity.Yogi;
import com.theinngu32.common.entity.constants.errorMessage.DuplicateValidationRule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;

import static com.theinngu32.common.utils.validation.duplicate.YogiDuplicateCheckerUtils.isDuplicate;

@AllArgsConstructor
@Getter
public enum YogiDuplicateErrorMessage implements DuplicateValidationRule<Yogi> {

    YOGI_ID("yogiId", "ယောဂီ၏ ယောဂီအမှတ်စဥ်သည် ကွန်ပျူတာစနစ်ထဲတွင် သိမ်းဆည်းထားပြီးဖြစ်ပါသည်။",
            (key, yogi) -> isDuplicate(key, yogi.getYogiId())
    ),
    NRC("nrc","ယောဂီ၏ မှတ်ပုံတင်အမှတ်သည် ကွန်ပျူတာစနစ်ထဲတွင် သိမ်းဆည်းထားပြီးဖြစ်ပါသည်။",
            (key, yogi) -> isDuplicate(key, yogi.getNrc())
    );

    // NRC duplicate in the import file
    // NRC is old yogi

    private final String value;
    private final String errorMessage;
    private final BiPredicate<String, Yogi> duplicateRule;
}
