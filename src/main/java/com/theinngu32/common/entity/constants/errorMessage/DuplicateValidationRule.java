package com.theinngu32.common.entity.constants.errorMessage;

import java.util.function.BiPredicate;

public interface DuplicateValidationRule<T> extends ErrorMessageBaseEnum<String> {
    BiPredicate<String, T> getDuplicateRule();
}

