package com.theinngu32.common.service.error;

import com.theinngu32.common.entity.User;
import com.theinngu32.common.entity.constants.errorMessage.ErrorType;
import com.theinngu32.common.entity.constants.errorMessage.user.UserDuplicateErrorMessage;
import com.theinngu32.common.entity.constants.errorMessage.user.UserInvalidFormatErrorMessage;
import com.theinngu32.common.entity.constants.errorMessage.user.UserRequireErrorMessage;
import com.theinngu32.common.repository.ValidationErrorRepository;
import org.springframework.stereotype.Service;

@Service
public class UserErrorService extends BaseErrorService<User>{

    private final ErrorMessageProviderFactory<User> errorMessageProviderFactory;

    public UserErrorService(ValidationErrorRepository validationErrorRepository,ErrorMessageProviderFactory<User> errorMessageProviderFactory){
        super(validationErrorRepository,errorMessageProviderFactory);
        this.errorMessageProviderFactory = errorMessageProviderFactory;
    }

    @Override
    public void registerErrorMessageProviders() {
        ErrorMessageProvider userProvider = new ErrorMessageProvider()
                .registerErrorMessageClass(ErrorType.REQUIRED_ERROR, UserRequireErrorMessage.class)
                .registerErrorMessageClass(ErrorType.INVALID_FORMAT_ERROR, UserInvalidFormatErrorMessage.class)
                .registerErrorMessageClass(ErrorType.DUPLICATE_ERROR, UserDuplicateErrorMessage.class);

        errorMessageProviderFactory.registerProvider(User.class,userProvider);
    }
}
