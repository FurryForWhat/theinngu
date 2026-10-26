package com.theinngu32.common.service.validation;

import com.theinngu32.common.entity.Yogi;
import com.theinngu32.common.entity.constants.errorMessage.tempYogi.TempYogiDuplicateErrorMessage;
import com.theinngu32.common.entity.constants.errorMessage.yogi.*;
import com.theinngu32.common.repository.ValidationErrorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class YogiValidationService extends BaseValidationService<Yogi> {

    public YogiValidationService(
            ValidationErrorRepository validationErrorRepository) {
        super(validationErrorRepository);
    }

    @Override
    public List<String> getRequiredFields() {
        return Stream.of(YogiRequireErrorMessage.values()).map(YogiRequireErrorMessage::getValue).toList();
    }

    @Override
    public Map<String, Pattern> getFormatFields() {
        return Stream.of(YogiInvalidFormatErrorMessage.values())
                .collect(Collectors.toMap(YogiInvalidFormatErrorMessage::getValue, YogiInvalidFormatErrorMessage::getPattern));
    }

    @Override
    public Map<String, Predicate<Yogi>> getBlacklistedFields() {
        return Stream.of(YogiBlacklistErrorMessage.values())
                .collect(Collectors.toMap(YogiBlacklistErrorMessage::getValue, YogiBlacklistErrorMessage::getBlacklistRule));
    }

    @Override
    public Map<String,Predicate<Yogi>> getIntegrityCheckFields(){
        return Stream.of(YogiDataIntegrityErrorMessage.values())
                .collect(Collectors.toMap(YogiDataIntegrityErrorMessage::getValue,YogiDataIntegrityErrorMessage::getIntegrityRule));
    }

    @Override
    public Map<String, BiPredicate<String, Yogi>> getUniqueFields() {
        return Stream.of(YogiDuplicateErrorMessage.values())
                .collect(Collectors.toMap(YogiDuplicateErrorMessage::getValue, YogiDuplicateErrorMessage::getDuplicateRule));
    }

}


