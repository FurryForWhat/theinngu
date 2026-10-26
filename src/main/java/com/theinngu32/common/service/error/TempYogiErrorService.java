package com.theinngu32.common.service.error;

import com.theinngu32.common.entity.TempYogi;
import com.theinngu32.common.entity.constants.errorMessage.ErrorType;
import com.theinngu32.common.entity.constants.errorMessage.tempYogi.*;
import com.theinngu32.common.repository.ValidationErrorRepository;
import org.springframework.stereotype.Service;

@Service
public class TempYogiErrorService extends BaseErrorService<TempYogi> {
    private final ErrorMessageProviderFactory<TempYogi> errorMessageProviderFactory;

    public TempYogiErrorService(
            ValidationErrorRepository validationErrorRepository,
            ErrorMessageProviderFactory<TempYogi> errorMessageProviderFactory
    ) {
        super(validationErrorRepository, errorMessageProviderFactory);
        this.errorMessageProviderFactory = errorMessageProviderFactory;
    }

    @Override
    public void registerErrorMessageProviders() {
        ErrorMessageProvider yogiProvider = new ErrorMessageProvider()
                .registerErrorMessageClass(ErrorType.REQUIRED_ERROR, TempYogiRequireErrorMessage.class)
                .registerErrorMessageClass(ErrorType.INVALID_FORMAT_ERROR, TempYogiInvalidFormatErrorMessage.class)
                .registerErrorMessageClass(ErrorType.DUPLICATE_ERROR, TempYogiDuplicateErrorMessage.class)
                .registerErrorMessageClass(ErrorType.DATA_INTEGRITY_ERROR, TempYogiDataIntegrityErrorMessage.class)
                .registerErrorMessageClass(ErrorType.BLACKLIST_ERROR, TempYogiBlacklistErrorMessage.class);

        errorMessageProviderFactory.registerProvider(TempYogi.class, yogiProvider);
    }
}