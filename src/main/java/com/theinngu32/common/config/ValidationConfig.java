package com.theinngu32.common.config;

import com.theinngu32.common.entity.*;
import com.theinngu32.common.service.validation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@RequiredArgsConstructor
public class ValidationConfig {

    private final YogiValidationService yogiValidationService;
    private final TempYogiValidationService tempYogiValidationService;
    private final OldYogiValidationService oldYogiValidationService;
    private final MeditationClassValidationService meditationClassValidationService;
    private final UserValidationService userValidationService;

    @Bean
    public ValidationServiceRegistry validationServiceRegistry() {
        return () -> {
            Map<Class<?>, BaseValidationService<?>> validationServices = new ConcurrentHashMap<>();
            validationServices.put(Yogi.class, yogiValidationService);
            validationServices.put(TempYogi.class, tempYogiValidationService);
            validationServices.put(OldYogi.class, oldYogiValidationService);
            validationServices.put(MeditationClass.class,meditationClassValidationService);
            validationServices.put(User.class,userValidationService);
            // add new service here
            return validationServices;
        };
    }

}
