package com.theinngu32.common.entity.constants.errorMessage;

import java.util.regex.Pattern;

public interface Regexable  extends ErrorMessageBaseEnum<String> {
    Pattern getPattern();
}
