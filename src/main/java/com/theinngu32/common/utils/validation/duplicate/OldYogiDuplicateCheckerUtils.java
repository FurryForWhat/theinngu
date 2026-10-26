package com.theinngu32.common.utils.validation.duplicate;

import com.theinngu32.common.entity.OldYogi;
import com.theinngu32.common.entity.constants.errorMessage.oldYogi.OldYogiDuplicateErrorMessage;
import com.theinngu32.common.repository.OldYogiRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class OldYogiDuplicateCheckerUtils {
    private static final Map<String, Function<String, Optional<List<OldYogi>>>> duplicateFunc = new HashMap<>();
    private final OldYogiRepository oldYogiRepository;

    @PostConstruct
    public void init() {
        duplicateFunc.put(
                OldYogiDuplicateErrorMessage.NRC.getValue(),
                oldYogiRepository::findByNrc
        );
    }

    public static boolean isDuplicate(String key, String value) {
        var func = duplicateFunc.get(key);
        return func != null && func.apply(value).isPresent();
    }
}
