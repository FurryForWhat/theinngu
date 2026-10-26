package com.theinngu32.common.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("within(com.theinngu32.common.controller..*)")
    public void printBefore(JoinPoint joinPoint){
        JSONObject logMap = new JSONObject();
        logMap.put("Class", joinPoint.getSignature().getDeclaringType());
        logMap.put("Method", joinPoint.getSignature().getName());

        JSONObject argumentLogMap =  new JSONObject();
        for(Object o:joinPoint.getArgs()){
            argumentLogMap.put("Argument", o);
            argumentLogMap.put("Data type", o.getClass());
        }
        logMap.put("Arguments", argumentLogMap);
        LOGGER.info(logMap.toString());
    }


    @AfterReturning(value = "within(com.theinngu32.common.controller..*)", returning = "results")
    public void printAfterLog(JoinPoint joinPoint, Object results) {
        LOGGER.info("Class name: {}\n", joinPoint.getSignature().getDeclaringType());
        LOGGER.info("Method name: {}\n", joinPoint.getSignature().getName());
        LOGGER.info("Return values: {}\n", results);

    }
}
