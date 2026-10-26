package com.theinngu32.common.service.validation;

import com.theinngu32.common.entity.User;
import com.theinngu32.common.entity.constants.errorMessage.user.UserDuplicateErrorMessage;
import com.theinngu32.common.entity.constants.errorMessage.user.UserInvalidFormatErrorMessage;
import com.theinngu32.common.entity.constants.errorMessage.user.UserRequireErrorMessage;
import com.theinngu32.common.repository.ValidationErrorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserValidationService extends BaseValidationService<User>{

    public UserValidationService(ValidationErrorRepository validationErrorRepository){
        super(validationErrorRepository);
    }

    @Override
    public List<String> getRequiredFields() {
        return Stream.of(UserRequireErrorMessage.values()).map(UserRequireErrorMessage::getValue).toList();
    }

    @Override
    public Map<String, Pattern> getFormatFields() {
        return Stream.of(UserInvalidFormatErrorMessage.values()).collect(Collectors.toMap(UserInvalidFormatErrorMessage::getValue,UserInvalidFormatErrorMessage::getPattern));
    }

    @Override
    public Map<String, BiPredicate<String, User>> getUniqueFields(){
        return Stream.of(UserDuplicateErrorMessage.values())
                .collect(Collectors.toMap(UserDuplicateErrorMessage::getValue,UserDuplicateErrorMessage::getDuplicateRule));
    }
}
