package com.theinngu32.common.service.error;

import java.util.Map;

public interface ErrorServiceRegistry {
    Map<String, BaseErrorService<?>> getErrorServices();
}
