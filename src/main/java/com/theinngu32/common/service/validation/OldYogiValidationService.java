package com.theinngu32.common.service.validation;

import com.theinngu32.common.entity.OldYogi;
import com.theinngu32.common.entity.constants.errorMessage.oldYogi.OldYogiDataIntegrityErrorMessage;
import com.theinngu32.common.entity.constants.errorMessage.oldYogi.OldYogiDuplicateErrorMessage;
import com.theinngu32.common.entity.constants.errorMessage.oldYogi.OldYogiInvalidFormatErrorMessage;
import com.theinngu32.common.entity.constants.errorMessage.oldYogi.OldYogiRequireErrorMessage;
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
public class OldYogiValidationService extends BaseValidationService<OldYogi> {

    public OldYogiValidationService(
            ValidationErrorRepository validationErrorRepository) {
        super(validationErrorRepository);
    }

    @Override
    public List<String> getRequiredFields() {
        return Stream.of(OldYogiRequireErrorMessage.values()).map(OldYogiRequireErrorMessage::getValue).toList();
    }

    @Override
    public Map<String, BiPredicate<String, OldYogi>> getUniqueFields() {
        return Stream.of(OldYogiDuplicateErrorMessage.values())
                .collect(Collectors.toMap(OldYogiDuplicateErrorMessage::getValue, OldYogiDuplicateErrorMessage::getDuplicateRule));
    }

    @Override
    public Map<String, Pattern> getFormatFields() {
        return Stream.of(OldYogiInvalidFormatErrorMessage.values())
                .collect(Collectors.toMap(OldYogiInvalidFormatErrorMessage::getValue, OldYogiInvalidFormatErrorMessage::getPattern));
    }

    @Override
    public Map<String, Predicate<OldYogi>> getIntegrityCheckFields() {
        return Stream.of(OldYogiDataIntegrityErrorMessage.values())
                .collect(Collectors.toMap(OldYogiDataIntegrityErrorMessage::getValue, OldYogiDataIntegrityErrorMessage::getIntegrityRule));
    }
}