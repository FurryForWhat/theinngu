package com.theinngu32.common.service.validation;

import com.theinngu32.common.entity.TempYogi;
import com.theinngu32.common.entity.constants.errorMessage.tempYogi.*;
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
public class TempYogiValidationService extends BaseValidationService<TempYogi> {

    public TempYogiValidationService(
            ValidationErrorRepository validationErrorRepository) {
        super(validationErrorRepository);
    }

    @Override
    public List<String> getRequiredFields() {
        return Stream.of(TempYogiRequireErrorMessage.values()).map(TempYogiRequireErrorMessage::getValue).toList();
    }

    @Override
    public Map<String, Pattern> getFormatFields() {
        return Stream.of(TempYogiInvalidFormatErrorMessage.values())
                .collect(Collectors.toMap(TempYogiInvalidFormatErrorMessage::getValue, TempYogiInvalidFormatErrorMessage::getPattern));
    }

    @Override
    public Map<String, Predicate<TempYogi>> getBlacklistedFields() {
        return Stream.of(TempYogiBlacklistErrorMessage.values())
                .collect(Collectors.toMap(TempYogiBlacklistErrorMessage::getValue, TempYogiBlacklistErrorMessage::getBlacklistRule));
    }

    @Override
    public Map<String, Predicate<TempYogi>> getIntegrityCheckFields() {
        return Stream.of(TempYogiDataIntegrityErrorMessage.values())
                .collect(Collectors.toMap(TempYogiDataIntegrityErrorMessage::getValue, TempYogiDataIntegrityErrorMessage::getIntegrityRule));
    }

    @Override
    public Map<String, BiPredicate<String, TempYogi>> getUniqueFields() {
        return Stream.of(TempYogiDuplicateErrorMessage.values())
                .collect(Collectors.toMap(TempYogiDuplicateErrorMessage::getValue, TempYogiDuplicateErrorMessage::getDuplicateRule));
    }
}