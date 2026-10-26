package com.theinngu32.common.aspect;

import com.theinngu32.common.entity.MasterData;
import com.theinngu32.common.entity.constants.errorMessage.ErrorAware;
import com.theinngu32.common.service.error.BaseErrorService;
import com.theinngu32.common.service.error.ErrorServiceRegistry;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Aspect
@Component
@RequiredArgsConstructor
@Order(2)
public class ErrorMappingAspect {

    private final ErrorServiceRegistry errorServiceRegistry;

    @AfterReturning(
            pointcut = "execution(* com.theinngu32.common.mapper.*.toDto(..)) || execution(* com.theinngu32.common.mapper.*.toDTO(..))",
            returning = "result"
    )
    public void addErrorsToDto(JoinPoint joinPoint, Object result) {
        if (result instanceof ErrorAware dto) {
            Object[] args = joinPoint.getArgs();
            if (args.length > 0 && args[0] instanceof MasterData entity) {
                var errorServices = errorServiceRegistry.getErrorServices();
                BaseErrorService<?> errorService = errorServices.get(entity.getClass().getSimpleName());
                if (errorService != null) {
                    dto.setErrors(errorService.getErrors(entity.getId(), entity.getClass().getSimpleName()));
                }
            }
        }
    }
}