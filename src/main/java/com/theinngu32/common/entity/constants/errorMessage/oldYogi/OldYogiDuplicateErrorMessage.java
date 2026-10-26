package com.theinngu32.common.entity.constants.errorMessage.oldYogi;

import com.theinngu32.common.entity.OldYogi;
import com.theinngu32.common.entity.constants.errorMessage.DuplicateValidationRule;
import com.theinngu32.common.utils.validation.duplicate.OldYogiDuplicateCheckerUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.BiPredicate;

@AllArgsConstructor
@Getter
public enum OldYogiDuplicateErrorMessage implements DuplicateValidationRule<OldYogi> {

    NRC("nrc", "ယောဂီ၏ မှတ်ပုံတင်အမှတ်သည် သိမ်းဆည်းထားပြီး ဖြစ်ပါသည်။",
            (key, oldYogi) -> OldYogiDuplicateCheckerUtils.isDuplicate(key, oldYogi.getNrc())
    );

    private final String value;
    private final String errorMessage;
    private final BiPredicate<String, OldYogi> duplicateRule;
}
