package com.theinngu32.common.aspect;

import com.theinngu32.common.entity.MasterData;
import com.theinngu32.common.service.validation.ValidationServiceProvider;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.core.config.Order;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Order(1)
public class ValidationAspect {

    private final ValidationServiceProvider validationServiceProvider;

    @AfterReturning(
            pointcut = "execution(* org.springframework.data.repository.CrudRepository.save(..)) || " +
                    "execution(* org.springframework.data.repository.CrudRepository.saveAll(..))",
            returning = "result"
    )
    public void validateAfterSave(Object result) {
        if (result instanceof MasterData entity) {
            validationServiceProvider.validate(entity);
        } else if (result instanceof Iterable<?> entities) {
            for (Object entity : entities) {
                if (entity instanceof MasterData masterData) {
                    validationServiceProvider.validate(masterData);
                }
            }
        }
    }
}
