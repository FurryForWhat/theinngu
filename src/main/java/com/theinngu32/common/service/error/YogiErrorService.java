package com.theinngu32.common.service.error;

import com.theinngu32.common.entity.Yogi;
import com.theinngu32.common.entity.constants.errorMessage.ErrorType;
import com.theinngu32.common.entity.constants.errorMessage.yogi.YogiDataIntegrityErrorMessage;
import com.theinngu32.common.entity.constants.errorMessage.yogi.YogiDuplicateErrorMessage;
import com.theinngu32.common.entity.constants.errorMessage.yogi.YogiInvalidFormatErrorMessage;
import com.theinngu32.common.entity.constants.errorMessage.yogi.YogiRequireErrorMessage;
import com.theinngu32.common.repository.ValidationErrorRepository;
import org.springframework.stereotype.Service;

@Service
public class YogiErrorService extends BaseErrorService<Yogi> {
    private final ErrorMessageProviderFactory<Yogi> errorMessageProviderFactory;

    public YogiErrorService(
            ValidationErrorRepository validationErrorRepository,
            ErrorMessageProviderFactory<Yogi> errorMessageProviderFactory
    ) {
        super(validationErrorRepository, errorMessageProviderFactory);
        this.errorMessageProviderFactory = errorMessageProviderFactory;
    }

    @Override
    public void registerErrorMessageProviders() {
        ErrorMessageProvider yogiProvider = new ErrorMessageProvider()
                .registerErrorMessageClass(ErrorType.REQUIRED_ERROR, YogiRequireErrorMessage.class)
                .registerErrorMessageClass(ErrorType.INVALID_FORMAT_ERROR, YogiInvalidFormatErrorMessage.class)
                .registerErrorMessageClass(ErrorType.DUPLICATE_ERROR, YogiDuplicateErrorMessage.class);

        errorMessageProviderFactory.registerProvider(Yogi.class, yogiProvider);
    }

}