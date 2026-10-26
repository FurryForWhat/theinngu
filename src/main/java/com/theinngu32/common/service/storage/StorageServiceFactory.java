package com.theinngu32.common.service.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class StorageServiceFactory {

    private final LocalStorageService localStorageService;
    private final AwsS3StorageService s3StorageService;
    private final String storageType;

    public StorageServiceFactory(LocalStorageService localStorageService,
                                 AwsS3StorageService s3StorageService,
                                 @Value("${storage.type}") String storageType) {
        this.localStorageService = localStorageService;
        this.s3StorageService = s3StorageService;
        this.storageType = storageType;
    }

    @Bean("storageService")
    public StorageService getStorageService() {
        if ("local".equalsIgnoreCase(storageType)) {
            return localStorageService;
        } else {
            return s3StorageService;
        }
    }
}
