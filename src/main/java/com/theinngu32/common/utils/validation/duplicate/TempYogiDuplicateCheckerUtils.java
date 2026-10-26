package com.theinngu32.common.utils.validation.duplicate;

import com.theinngu32.common.entity.TempYogi;
import com.theinngu32.common.entity.constants.errorMessage.tempYogi.TempYogiDuplicateErrorMessage;
import com.theinngu32.common.repository.TempYogiRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.function.TriFunction;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TempYogiDuplicateCheckerUtils {
    private static final Map<String, TriFunction<String, String, Long, Optional<TempYogi>>> duplicateFunc = new HashMap<>();

    private final TempYogiRepository tempYogiRepository;

    @PostConstruct
    public void init() {
        duplicateFunc.put(
                TempYogiDuplicateErrorMessage.PASSPORT.getValue(),
                tempYogiRepository::findByPassportAndRecordTypeAndRecordId
        );
        duplicateFunc.put(
                TempYogiDuplicateErrorMessage.YOGI_ID.getValue(),
                tempYogiRepository::findByYogiIdAndRecordTypeAndRecordId
        );
        duplicateFunc.put(
                TempYogiDuplicateErrorMessage.NRC.getValue(),
                tempYogiRepository::findByNrcAndRecordTypeAndRecordId
        );
    }

    public static boolean isDuplicate(String key, TempYogi tempYogi) {
        var func = duplicateFunc.get(key);
        Class<?> tempYogiClass = tempYogi.getClass();
        String str = "";
        try {
            var field = tempYogiClass.getField(key);
            field.setAccessible(true);
            Object value = field.get(tempYogiClass);
            if ( value instanceof String strValue ) {
                str = strValue;
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return func != null && func.apply(str, tempYogi.getRecordType(), tempYogi.getRecordId()).isPresent();
    }

}
