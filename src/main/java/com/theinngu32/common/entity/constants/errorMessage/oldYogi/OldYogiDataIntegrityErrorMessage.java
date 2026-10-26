package com.theinngu32.common.entity.constants.errorMessage.oldYogi;

import com.theinngu32.common.entity.OldYogi;
import com.theinngu32.common.entity.constants.errorMessage.DataIntegrityRule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Predicate;

import static com.theinngu32.common.utils.validation.IntegrityCheckerUtils.isValidNrc;

@Getter
@AllArgsConstructor
public enum OldYogiDataIntegrityErrorMessage implements DataIntegrityRule<OldYogi> {

    NRC("nrc", "မှတ်ပုံတင်အမှတ် မှားယွင်း နေပါသည်။",
            oldYogi -> isValidNrc(oldYogi.getNrc())
    );

    private final String value;
    private final String errorMessage;
    private final Predicate<OldYogi> integrityRule;
}