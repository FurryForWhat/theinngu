package com.theinngu32.common.entity.constants.errorMessage;

import java.util.function.Predicate;

public interface DataIntegrityRule<T> extends ErrorMessageBaseEnum<String> {
    Predicate<T> getIntegrityRule();
}

