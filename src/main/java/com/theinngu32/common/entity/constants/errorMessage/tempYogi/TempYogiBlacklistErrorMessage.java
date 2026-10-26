package com.theinngu32.common.entity.constants.errorMessage.tempYogi;

import com.theinngu32.common.entity.TempYogi;
import com.theinngu32.common.entity.constants.errorMessage.BlacklistValidationRule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Predicate;

import static com.theinngu32.common.utils.validation.BlacklistCheckerUtils.*;

@Getter
@AllArgsConstructor
public enum TempYogiBlacklistErrorMessage implements BlacklistValidationRule<TempYogi> {

//    PASSPORT("passport", "ယောဂီ ၏ passport သည် BlackList ဖြစ်နေပါသည်။",
//            BlacklistCheckerUtils::isBlacklistedByPassport
//    ),

    NRC("nrc", "ယောဂီ၏ မှတ်ပုံတင်အမှတ်သည် BlackList ဖြစ်နေပါသည်။",
            TempYogi -> isBlacklistedByNrc(TempYogi.getNrc()) && isBlacklistedByYogiNrc(TempYogi.getNrc())
    ),
    YOGI_ID("yogiId", "ယောဂီအမှတ်သည် BlackList ဖြစ်နေပါသည်။",
            TempYogi -> isBlacklistedByYogiId(TempYogi.getYogiId())
    );

    private final String value;
    private final String errorMessage;
    private final Predicate<TempYogi> blacklistRule;
}
