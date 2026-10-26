package com.theinngu32.common.entity.constants.errorMessage.tempYogi;

import com.theinngu32.common.entity.TempYogi;
import com.theinngu32.common.entity.constants.errorMessage.DataIntegrityRule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Predicate;

import static com.theinngu32.common.utils.validation.IntegrityCheckerUtils.*;

@Getter
@AllArgsConstructor
public enum TempYogiDataIntegrityErrorMessage implements DataIntegrityRule<TempYogi> {


    BIRTH_DATE("birthDate", "မွေးသက္ကရာဇ် မှားယွင်းနေပါသည်။",
            tempYogi -> isValidDate(
                    tempYogi.getDobDay(),
                    tempYogi.getDobMonth(),
                    tempYogi.getDobYear()
            )
    ),
    MONK_DATE("monkDate", "သာသနာ့ဘောင်ဝင်သည့်ခုနှစ် မှားယွင်းနေပါသည်။",
            tempYogi -> isValidDate(
                    tempYogi.getMonkDay(),
                    tempYogi.getMonkMonth(),
                    tempYogi.getMonkYear()
            )
    ),
    CITY("city", "ယောဂီနေထိုင်သည့် မြို့အမည်သည် မှားယွင်းနေပါသည်။",
            tempYogi -> isValidLocation(
                    tempYogi.getRegion(),
                    tempYogi.getCity()
            )
    ),
    NRC("nrc", "ယောဂီ၏ မှတ်ပုံတင်အမှတ်သည် မှားယွင်းနေပါသည်။",
            tempYogi -> isValidNrc(
                    tempYogi.getNrcPrefixCode(),
                    tempYogi.getNrcDivision(),
                    tempYogi.getNrcType(),
                    tempYogi.getNrcPostFixDigit()
            )
    );

    // need to check region

    private final String value;
    private final String errorMessage;
    private final Predicate<TempYogi> integrityRule;

}