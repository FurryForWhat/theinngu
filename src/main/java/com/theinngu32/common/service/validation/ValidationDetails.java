package com.theinngu32.common.service.validation;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public interface ValidationDetails<T> extends Serializable {

    default List<String> getRequiredFields() {
        return Collections.emptyList();
    }

    default Map<String, Pattern> getFormatFields() {
        return Collections.emptyMap();
    }

    default Map<String, BiPredicate<String, T>> getUniqueFields() {
        return Collections.emptyMap();
    }

    default Map<String, Predicate<T>>  getBlacklistedFields() {
        return Collections.emptyMap();
    }

    default Map<String, Predicate<T>> getIntegrityCheckFields() {
        return Collections.emptyMap();
    }
}
