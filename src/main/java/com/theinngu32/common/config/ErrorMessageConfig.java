package com.theinngu32.common.config;

import com.theinngu32.common.entity.*;
import com.theinngu32.common.service.error.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class ErrorMessageConfig {

    private final YogiErrorService yogiErrorService;
    private final MeditationClassErrorService meditationClassErrorService;
    private final UserErrorService userErrorService;
    private final TempYogiErrorService tempYogiErrorService;
    private final OldYogiErrorService oldYogiErrorService;

    @Bean
    public ErrorServiceRegistry errorServiceRegistry() {
       return () -> {
            Map<String, BaseErrorService<?>> errorServices = new HashMap<>();
            errorServices.put(Yogi.class.getSimpleName(), yogiErrorService);
            errorServices.put(MeditationClass.class.getSimpleName(), meditationClassErrorService);
            errorServices.put(User.class.getSimpleName(), userErrorService);
            errorServices.put(TempYogi.class.getSimpleName(), tempYogiErrorService);
            errorServices.put(OldYogi.class.getSimpleName(), oldYogiErrorService);
            // add new Error Service here
            return errorServices;
        };
    }
}
