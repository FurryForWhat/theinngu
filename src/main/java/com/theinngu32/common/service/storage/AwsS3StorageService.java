package com.theinngu32.common.service.storage;

import com.theinngu32.common.repository.StorageAttachmentRepository;
import com.theinngu32.common.repository.StorageBlobRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class AwsS3StorageService extends StorageService {
    private final S3Client s3Client;
    private final PlatformTransactionManager transactionManager;

    @Value("${aws.bucket.key.prefix}")
    private String bucketKeyPrefix;

    @Value("${aws.bucket.name}")
    private String bucketName;

    public AwsS3StorageService(
            StorageBlobRepository storageBlobsRepository,
            StorageAttachmentRepository storageAttachmentsRepository,
            S3Client s3Client,
            PlatformTransactionManager transactionManager
    ) {
        super(storageBlobsRepository, storageAttachmentsRepository);
        this.s3Client = s3Client;
        this.transactionManager = transactionManager;
    }

    @Override
    public boolean uploadFile(File file, String prefix, String key) {
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(prefix + FILE_SEPARATOR + key)
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromFile(file));
            if (!file.delete()) {
                log.warn("Failed to delete temporary file: {}", file.getAbsolutePath());
            }
            return true;
        } catch (S3Exception e) {
            log.error("S3 upload failed for key {}: {}", key, e.getMessage());
            return false;
        }
    }

    @Override
    public Optional<Resource> retrieveFile(String key) {
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(getAbsoluteName(key))
                    .build();

            ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(getObjectRequest);
            return Optional.of(new ByteArrayResource(objectBytes.asByteArray()));
        } catch (S3Exception e) {
            log.error("Failed to retrieve file from S3 with key {}: {}", key, e.getMessage());
            return Optional.empty();
        }
    }

    @Async
    @Override
    public CompletableFuture<Boolean> deleteFile(String key) {
        try {
            DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(getAbsoluteName(key))
                    .build();

            s3Client.deleteObject(deleteRequest);
            removeBlobMetadata(key, transactionManager);
            return CompletableFuture.completedFuture(true);
        } catch (S3Exception e) {
            log.error("Failed to delete file from S3 with key {}: {}", key, e.getMessage());
            return CompletableFuture.completedFuture(false);
        }
    }

    @Override
    public String getStoragePrefix(String recordType) {
        return bucketKeyPrefix + FILE_SEPARATOR + recordType;
    }

    @Override
    protected String getServiceName() {
        return "amazon";
    }
}

