package com.theinngu32.common.entity.constants.errorMessage;

import java.util.function.Predicate;

public interface BlacklistValidationRule<T> extends ErrorMessageBaseEnum<String> {
    Predicate<T> getBlacklistRule();
}

