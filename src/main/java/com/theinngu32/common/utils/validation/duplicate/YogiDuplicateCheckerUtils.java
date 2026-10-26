package com.theinngu32.common.utils.validation.duplicate;

import com.theinngu32.common.entity.OldYogi;
import com.theinngu32.common.entity.Yogi;
import com.theinngu32.common.entity.constants.errorMessage.yogi.YogiDuplicateErrorMessage;
import com.theinngu32.common.repository.OldYogiRepository;
import com.theinngu32.common.repository.YogiRepository;
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
public class YogiDuplicateCheckerUtils {

    private static final Map<String, Function<String, Optional<Yogi>>> duplicateFunc = new HashMap<>();

    private final YogiRepository yogiRepository;
    private final OldYogiRepository oldYogiRepository;

    @PostConstruct
    public void init(){
        duplicateFunc.put(
                YogiDuplicateErrorMessage.YOGI_ID.getValue(),
                yogiRepository::findByYogiId
        );
        duplicateFunc.put(
                YogiDuplicateErrorMessage.NRC.getValue(),
                yogiRepository::findByNrc
        );
    }

    public static boolean isDuplicate(String key, String value) {
        var func = duplicateFunc.get(key);
        return func != null && func.apply(value).isPresent();
    }
}
