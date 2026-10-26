package com.theinngu32.common.entity.constants.errorMessage.user;

import com.theinngu32.common.entity.User;
import com.theinngu32.common.entity.constants.errorMessage.DuplicateValidationRule;
import com.theinngu32.common.utils.validation.duplicate.UserDuplicateCheckerUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.BiPredicate;

@AllArgsConstructor
@Getter
public enum UserDuplicateErrorMessage implements DuplicateValidationRule<User> {

    EMAIL("email","အီးမေးလ်သည် အသုံးပြုပြီးသားဖြစ်ပါသည်။",
            (key, user) -> UserDuplicateCheckerUtils.isDuplicate(key, user.getEmail())
    );

    private final String value;
    private final String errorMessage;
    private final BiPredicate<String, User> duplicateRule;
}
