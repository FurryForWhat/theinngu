package com.theinngu32.common.service.storage;

import com.theinngu32.common.repository.StorageAttachmentRepository;
import com.theinngu32.common.repository.StorageBlobRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@Service
@Slf4j
public class LocalStorageService extends StorageService {
    private final PlatformTransactionManager transactionManager;
    private static final String USER_HOME = System.getProperty("user.home");

    @Value("${local.storage.folder.name:Documents/theinngu-file-storage}")
    private String localStorageFolderName;

    public LocalStorageService(
            StorageBlobRepository storageBlobsRepository,
            StorageAttachmentRepository storageAttachmentsRepository,
            PlatformTransactionManager transactionManager
    ) {
        super(storageBlobsRepository, storageAttachmentsRepository);
        this.transactionManager = transactionManager;
    }

    @Override
    public boolean uploadFile(File file, String prefix, String fileName) {
        try {
            String userHome = System.getProperty("user.home");
            Path storagePath = Paths.get(userHome, prefix);

            if (!Files.exists(storagePath)) {
                Files.createDirectories(storagePath);
            }
            Path targetPath = storagePath.resolve(fileName);
            Files.copy(file.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            if (!file.delete()) {
                log.warn("Failed to delete temporary file: {}", file.getAbsolutePath());
            }
            return true;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public Optional<Resource> retrieveFile(String filename) {
        Path storagePath = Paths.get(USER_HOME, getAbsoluteName(filename));

        if (Files.exists(storagePath)) {
            Resource resource = new FileSystemResource(storagePath);
            return Optional.of(resource);
        } else {
            log.warn("File not found for key: {}", filename);
            return Optional.empty();
        }
    }

    @Async
    @Override
    public CompletableFuture<Boolean> deleteFile(String filename) {
        try {
            Path storagePath = Paths.get(USER_HOME, getAbsoluteName(filename));

            if (Files.exists(storagePath)) {
                Files.delete(storagePath);
                removeBlobMetadata(filename, transactionManager);
                return CompletableFuture.completedFuture(true);
            } else {
                log.warn("File not found for deletion: {}", filename);
                return CompletableFuture.completedFuture(false);
            }
        } catch (IOException e) {
            log.error("Error deleting file for key: {}", filename, e);
            return CompletableFuture.completedFuture(false);
        }
    }

    @Override
    public String getStoragePrefix(String recordType) {
        return localStorageFolderName + FILE_SEPARATOR + recordType;
    }

    @Override
    protected String getServiceName() {
        return "local";
    }

}

