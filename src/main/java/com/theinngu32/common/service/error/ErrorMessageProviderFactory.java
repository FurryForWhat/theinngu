package com.theinngu32.common.service.error;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class ErrorMessageProviderFactory<T> {
    private final Map<Class<? extends T>, ErrorMessageProvider> providers = new HashMap<>();

    public void registerProvider(Class<? extends T> entityClass, ErrorMessageProvider provider) {
        providers.put(entityClass, provider);
    }

    public Optional<ErrorMessageProvider> getProvider(Class<? extends T> entityClass) {
        return Optional.ofNullable(providers.get(entityClass));
    }
    
    public Optional<ErrorMessageProvider> getProviderByName(String entityName) {
        return providers.entrySet().stream()
                .filter(entry -> entry.getKey().getSimpleName().equals(entityName))
                .map(Map.Entry::getValue)
                .findFirst();
    }
}