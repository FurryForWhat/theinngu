package com.theinngu32.common.service.storage;

import com.theinngu32.common.dto.FileUploadRequestDto;
import com.theinngu32.common.entity.StorageAttachment;
import com.theinngu32.common.entity.StorageBlob;
import com.theinngu32.common.exception.ChecksumFailedException;
import com.theinngu32.common.exception.FileUploadFailedException;
import com.theinngu32.common.exception.ResourceNotFoundException;
import com.theinngu32.common.repository.StorageAttachmentRepository;
import com.theinngu32.common.repository.StorageBlobRepository;
import com.theinngu32.common.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.UUID;
import java.util.Base64;
import java.util.concurrent.CompletableFuture;

import static com.theinngu32.common.utils.FileUtils.getMetadata;
import static com.theinngu32.common.utils.FileUtils.getFileExtension;
import static com.theinngu32.common.utils.ImageUtils.compressOnlyIfLargeImage;
import static com.theinngu32.common.utils.constants.ApiEndpoints.STORAGE_BASE_URL;

@RequiredArgsConstructor
@Slf4j
public abstract class StorageService {
    private final StorageBlobRepository storageBlobsRepository;
    private final StorageAttachmentRepository storageAttachmentsRepository;
    protected final char FILE_SEPARATOR = '/';

    @Value("${file.size.compress-threshold:3MB}")
    private String maxFileSizeValue;

    /**
     * Uploads a file and saves metadata.
     */
    @Async
    public void uploadAndSave(FileUploadRequestDto requestDto, String attachmentName, String recordType, Long recordId) {
        String key = generateKey(getFileExtension(requestDto.filename()));
        String prefix = getStoragePrefix(recordType);
        StorageBlob storageBlob = extractFileMetadata(requestDto, key);
        File file = null;
        try {
            file = compressOnlyIfLargeImage(requestDto, new FileUtils().parseStorageSize(maxFileSizeValue));
        } catch (IOException e) {
            throw new FileUploadFailedException(e.getMessage());
        }

        boolean uploaded = uploadFile(file, prefix, key);

        if (!uploaded) {
            throw new FileUploadFailedException("File upload failed");
        }

        storageBlobsRepository.save(storageBlob);

        StorageAttachment attachment = new StorageAttachment();
        attachment.setName(attachmentName);
        attachment.setRecordType(recordType);
        attachment.setRecordId(recordId);
        attachment.setStorageBlob(storageBlob);
        storageAttachmentsRepository.save(attachment);
    }

    /**
     * Uploads a file to storage.
     *
     * @return True if successful, false otherwise
     */
    public abstract boolean uploadFile(File file, String prefix, String key);

    /**
     * Retrieve file by key (filename)
     *
     * @param key string
     * @return file with Optional wrap
     */
    public abstract Optional<Resource> retrieveFile(String key);

    /**
     * Delete file by key (filename)
     *
     * @param key string
     * @return True if delete successful, false otherwise
     */
    @Async
    public abstract CompletableFuture<Boolean> deleteFile(String key);

    /**
     * Extracts file metadata.
     */
    private StorageBlob extractFileMetadata(FileUploadRequestDto dto, String key) {
        StorageBlob blob = new StorageBlob();
        blob.setKey(key);
        blob.setFilename(dto.filename());
        blob.setContentType(dto.contentType());
        blob.setServiceName(getServiceName());
        blob.setMetadata(getMetadata(dto.bytes(), dto.extension()));
        blob.setChecksum(generateChecksum(dto.bytes()));
        return blob;
    }

    /**
     * Generates SHA-256 checksum for the file.
     */
    public String generateChecksum(byte[] data) {
        if (data == null || data.length == 0) {
            throw new IllegalArgumentException("Data cannot be null or empty");
        }

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            try (InputStream inputStream = new ByteArrayInputStream(data)) {
                byte[] buffer = new byte[1024];
                int bytesRead;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    digest.update(buffer, 0, bytesRead);
                }
            }

            byte[] hash = digest.digest();
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException | IOException e) {
            throw new ChecksumFailedException("Failed to generate checksum", e);
        }
    }

    /**
     * Generates a unique key with file extension suffix.
     */
    private static String generateKey(String extension) {
        long timestamp = System.nanoTime();
        String uniqueID = UUID.randomUUID().toString().replace("-", "");
        return timestamp + uniqueID + "." + extension;
    }

    /**
     * Returns the record type
     */
    protected String getRecordType(String key){
        return storageAttachmentsRepository.findRecordTypeByKey(key)
                .orElseThrow(()-> new ResourceNotFoundException("Key %s not found".formatted(key)));
    }

    /**
     * Returns filename
     */
    public String getFilenameByKey(String key){
        return storageBlobsRepository.findFilenameByKey(key)
                .orElseThrow(()-> new ResourceNotFoundException("Key %s not found".formatted(key)));
    }

    public String getKey(String recordType, Long recordId, String name) {
        return storageAttachmentsRepository.findKeyByRecordTypeAndRecordIdAndName(recordType, recordId, name)
                .orElseThrow(() -> new ResourceNotFoundException("Storage Attachment Not Found for (%s, %d, %s)"
                        .formatted(recordType, recordId, name)));
    }

    public String getFileUrl(String recordType, Long recordId, String name){
        String key = getKey(recordType, recordId, name);
        return "%s/retrieve/%s".formatted(STORAGE_BASE_URL, key);
    }

    protected void removeBlobMetadata(String key, PlatformTransactionManager transactionManager) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);

        StorageBlob blob = storageBlobsRepository.findByKey(key)
                .orElseThrow(()-> new ResourceNotFoundException("Key %s not found".formatted(key)));
        storageAttachmentsRepository.deleteByStorageBlob(blob);
        storageBlobsRepository.delete(blob);
        transactionManager.commit(status);
    }

    /**
     * Returns the absolute name
     */
    protected String getAbsoluteName(String key) {
        return getStoragePrefix(getRecordType(key)) + FILE_SEPARATOR + key;
    }

    /**
     * Returns the storage path prefix
     */
    public abstract String getStoragePrefix(String recordType);

    /**
     * Returns the storage service name.
     */
    protected abstract String getServiceName();
}

