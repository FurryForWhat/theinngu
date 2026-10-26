package com.theinngu32.common.service.validation;

import com.theinngu32.common.entity.MeditationClass;
import com.theinngu32.common.entity.constants.errorMessage.meditationClass.MeditationClassInvalidFormatErrorMessage;
import com.theinngu32.common.entity.constants.errorMessage.meditationClass.MeditationClassRequireErrorMessage;
import com.theinngu32.common.repository.ValidationErrorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MeditationClassValidationService extends BaseValidationService <MeditationClass> {

    public MeditationClassValidationService(ValidationErrorRepository validationErrorRepository) {
        super(validationErrorRepository);
    }

    @Override
    public List<String> getRequiredFields() {
        return Stream.of(MeditationClassRequireErrorMessage.values()).map(MeditationClassRequireErrorMessage::getValue).toList();
    }

    @Override
    public Map<String, Pattern> getFormatFields() {
        return Stream.of(MeditationClassInvalidFormatErrorMessage.values())
                .collect(Collectors.toMap(MeditationClassInvalidFormatErrorMessage::getValue, MeditationClassInvalidFormatErrorMessage::getPattern));
    }

}