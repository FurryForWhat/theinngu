package com.theinngu32.common.entity.constants.errorMessage;

import com.theinngu32.common.entity.constants.BaseEnum;

public interface ErrorMessageBaseEnum<V>  extends BaseEnum<V> {
    V getErrorMessage();
}
