package com.theinngu32.common.service.error;
import com.theinngu32.common.entity.OldYogi;
import com.theinngu32.common.entity.constants.errorMessage.ErrorType;
import com.theinngu32.common.entity.constants.errorMessage.oldYogi.OldYogiDataIntegrityErrorMessage;
import com.theinngu32.common.entity.constants.errorMessage.oldYogi.OldYogiDuplicateErrorMessage;
import com.theinngu32.common.entity.constants.errorMessage.oldYogi.OldYogiInvalidFormatErrorMessage;
import com.theinngu32.common.entity.constants.errorMessage.oldYogi.OldYogiRequireErrorMessage;
import com.theinngu32.common.repository.ValidationErrorRepository;
import org.springframework.stereotype.Service;

@Service
public class OldYogiErrorService extends BaseErrorService<OldYogi> {
    private final ErrorMessageProviderFactory<OldYogi> errorMessageProviderFactory;

    public OldYogiErrorService(ValidationErrorRepository validationErrorRepository,
                               ErrorMessageProviderFactory<OldYogi> errorMessageProviderFactory) {
        super(validationErrorRepository, errorMessageProviderFactory);
        this.errorMessageProviderFactory = errorMessageProviderFactory;
    }

    @Override
    public void registerErrorMessageProviders() {
        ErrorMessageProvider yogiProvider = new ErrorMessageProvider()
                .registerErrorMessageClass(ErrorType.REQUIRED_ERROR, OldYogiRequireErrorMessage.class)
                .registerErrorMessageClass(ErrorType.INVALID_FORMAT_ERROR, OldYogiInvalidFormatErrorMessage.class)
                .registerErrorMessageClass(ErrorType.DUPLICATE_ERROR, OldYogiDuplicateErrorMessage.class)
                .registerErrorMessageClass(ErrorType.DATA_INTEGRITY_ERROR, OldYogiDataIntegrityErrorMessage.class);
        errorMessageProviderFactory.registerProvider(OldYogi.class, yogiProvider);
    }
}
