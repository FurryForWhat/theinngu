package com.theinngu32.common.entity.constants.errorMessage.yogi;

import com.theinngu32.common.entity.Yogi;
import com.theinngu32.common.entity.constants.errorMessage.BlacklistValidationRule;
import com.theinngu32.common.utils.validation.BlacklistCheckerUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Predicate;

@Getter
@AllArgsConstructor
public enum YogiBlacklistErrorMessage implements BlacklistValidationRule<Yogi> {

    YOGI_ID("yogiId","ယောဂီ၏ ယောဂီအမှတ်စဥ်သည် BlackList ဖြစ်နေပါသည်။",
            yogi -> BlacklistCheckerUtils.isBlacklistedByYogiId(yogi.getYogiId())
    ),
    REMARK("remark","ယောဂီ ၏ remark သည် BlackList ဖြစ်နေပါသည်။",
            yogi -> BlacklistCheckerUtils.isBlacklistedByRemark(yogi.getRemark())
    ),
    NRC("nrc","ယောဂီ ၏ nrc သည် BlackList ဖြစ်နေပါသည်။",
            yogi -> BlacklistCheckerUtils.isBlacklistedByYogiNrc(yogi.getNrc())
    );

    private final String value;
    private final String errorMessage;
    private final Predicate<Yogi> blacklistRule;
}
