package com.theinngu32.common.utils.validation;

import com.theinngu32.common.entity.constants.NrcType;
import com.theinngu32.common.repository.OldYogiRepository;
import com.theinngu32.common.repository.YogiNrcRepository;
import com.theinngu32.common.repository.YogiRepository;
import com.theinngu32.common.utils.NrcUtil;
import com.theinngu32.common.utils.constants.RegexUtils;
import com.theinngu32.common.utils.function.PentaFunction;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
@Slf4j
public class BlacklistCheckerUtils {

    private static final Map<String, Function<String, Optional<Boolean>>> blacklistFunc = new HashMap<>();
    private static final Map<String, PentaFunction<String,String,String, NrcType,String, Optional<Boolean>>> yogiNrcBlacklistFunc = new HashMap<>();
    private static final String[] BLACKLISTED_KEYWORDS = {"banned", "blacklisted"};

    private final YogiRepository yogiRepository;
    private final OldYogiRepository oldYogiRepository;
    private final YogiNrcRepository yogiNrcRepository;

    @PostConstruct
    public void init() {
        blacklistFunc.put("nrc", oldYogiRepository::isBlacklistedByNrc);
        blacklistFunc.put("yogiId", yogiRepository::isBlacklistedByYogiId);
        yogiNrcBlacklistFunc.put("nrc", yogiNrcRepository::isBlacklistedByNrc);
    }

    public static boolean isBlacklistedByYogiNrc(String nrc) {
        return Optional.ofNullable(nrc)
                .flatMap(NrcUtil::parseNRC)
                .map(nrcData -> isBlacklistedByYogiNrc(nrcData.prefixCode(), nrcData.regionName(), nrcData.type(), nrcData.serialNumber()))
                .orElse(false);
    }

    public static boolean isBlacklistedByYogiNrc(String prefixCode, String name, NrcType type, String postFixCode) {
        var checker = yogiNrcBlacklistFunc.get("nrc");
        if (checker == null) {
            log.warn("No blacklist function found for 'yogiNrc'");
            return false;
        }
        return checker.apply(prefixCode, name, name, type, postFixCode).orElse(false);
    }

    private static NrcType convertNrcType(String value) {
        return Arrays.stream(NrcType.values())
                .filter(nrcType -> nrcType.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No NrcType found for value: " + value));
    }

    public static boolean isBlacklistedByNrc(String nrc) {
        var checker = blacklistFunc.get("nrc");
        if (checker == null) {
            return false;
        }
        return checker.apply(nrc).orElse(false);
    }

    public static boolean isBlacklistedByYogiId(String yogiId) {
        var checker = blacklistFunc.get("yogiId");
        if (checker == null) {
            return false;
        }
        return checker.apply(yogiId).orElse(false);
    }

    public static boolean isBlacklistedByRemark(String remark) {
        if (remark == null || remark.isBlank()) {
            return false;
        }
        for (String keyword : BLACKLISTED_KEYWORDS) {
            if (remark.toLowerCase().contains(keyword)) {
                return true;
            }
        }
        return false;
    }

}
