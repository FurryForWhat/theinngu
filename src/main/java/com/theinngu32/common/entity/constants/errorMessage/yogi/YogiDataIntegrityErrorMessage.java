package com.theinngu32.common.entity.constants.errorMessage.yogi;

import com.theinngu32.common.entity.Yogi;
import com.theinngu32.common.entity.constants.errorMessage.DataIntegrityRule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Predicate;

import static com.theinngu32.common.utils.validation.IntegrityCheckerUtils.*;

@Getter
@AllArgsConstructor
public enum YogiDataIntegrityErrorMessage implements DataIntegrityRule<Yogi> {

    CITY("city", "ယောဂီနေထိုင်သည့် မြို့မှားယွင်းနေပါသည်။",
        yogi -> isValidLocation(
                yogi.getRegion().getNameEn(),
                yogi.getCity().getNameEn()
        )
    ),
    NRC("nrc","ယောဂီမှတ်ပုံတင် မှားယွင်းနေပါသည်",
            yogi -> isValidNrc(yogi.getNrc())
    ),
    GENDER("genderType", "ကျား၊ မ၊ သို့မဟုတ် သတ်မှတ်ထားသော သာသနာဝင် တစ်ဦးဖြစ်ရမည်။",
            yogi -> isValidGender(yogi.getGenderType().name())
    );


    private final String value;
    private final String errorMessage;
    private final Predicate<Yogi> integrityRule;
}
