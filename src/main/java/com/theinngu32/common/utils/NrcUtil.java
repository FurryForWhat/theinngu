package com.theinngu32.common.utils;

import com.theinngu32.common.entity.constants.NrcType;
import com.theinngu32.common.utils.constants.RegexUtils;
import com.theinngu32.common.utils.validation.IntegrityCheckerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Optional;

@Component
@Slf4j
public class NrcUtil {

    private static final Pattern NRC_PATTERN = Pattern.compile(RegexUtils.NRC_REGEX_GROUP);

    private static NrcType convertNrcType(String value) {
        return Arrays.stream(NrcType.values())
                .filter(nrcType -> nrcType.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No NrcType found for value: " + value));
    }

    public static Optional<NrcData> parseNRC(String nrc) {
        if (nrc == null || nrc.isBlank()) {
            log.warn("Received null or empty NRC input.");
            return Optional.empty();
        }

        Matcher matcher = NRC_PATTERN.matcher(nrc);
        if (!matcher.matches()) {
            log.warn("Input does not match NRC format: {}", nrc);
            return Optional.empty();
        }

        String prefixCode = matcher.group(1);
        String regionName = matcher.group(2);
        String citizenshipType = matcher.group(3);
        String serialNumber = matcher.group(4);

        try {
            String typeValue = citizenshipType.substring(1, citizenshipType.length() - 1);
            NrcType type = convertNrcType(typeValue);

            if (!IntegrityCheckerUtils.isValidNrc(prefixCode, regionName, citizenshipType, serialNumber)) {
                log.warn("Invalid NRC format detected: {}", nrc);
                return Optional.empty();
            }

            return Optional.of(new NrcData(prefixCode, regionName, type, serialNumber));

        } catch (IllegalArgumentException e) {
            log.warn("Invalid NRC type encountered in string: {}", nrc);
            return Optional.empty();
        }
    }

    public record NrcData(String prefixCode, String regionName, NrcType type, String serialNumber) {}
}

