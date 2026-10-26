package com.theinngu32.common.service.error;

import com.theinngu32.common.entity.MeditationClass;
import com.theinngu32.common.entity.constants.errorMessage.ErrorType;
import com.theinngu32.common.entity.constants.errorMessage.meditationClass.MeditationClassDataIntegrityErrorMessage;
import com.theinngu32.common.entity.constants.errorMessage.meditationClass.MeditationClassInvalidFormatErrorMessage;
import com.theinngu32.common.entity.constants.errorMessage.meditationClass.MeditationClassRequireErrorMessage;
import com.theinngu32.common.repository.ValidationErrorRepository;
import org.springframework.stereotype.Service;

@Service
public class MeditationClassErrorService extends BaseErrorService<MeditationClass> {
    private final ErrorMessageProviderFactory<MeditationClass> errorMessageProviderFactory;

    public MeditationClassErrorService(
            ValidationErrorRepository validationErrorRepository,
            ErrorMessageProviderFactory<MeditationClass> errorMessageProviderFactory
    ) {
        super(validationErrorRepository, errorMessageProviderFactory);
        this.errorMessageProviderFactory = errorMessageProviderFactory;
    }

    @Override
    public void registerErrorMessageProviders() {
        ErrorMessageProvider meditationClassProvider = new ErrorMessageProvider()
                .registerErrorMessageClass(ErrorType.REQUIRED_ERROR, MeditationClassRequireErrorMessage.class)
                .registerErrorMessageClass(ErrorType.INVALID_FORMAT_ERROR, MeditationClassInvalidFormatErrorMessage.class)
                .registerErrorMessageClass(ErrorType.DATA_INTEGRITY_ERROR, MeditationClassDataIntegrityErrorMessage.class);

        errorMessageProviderFactory.registerProvider(MeditationClass.class, meditationClassProvider);
    }
}