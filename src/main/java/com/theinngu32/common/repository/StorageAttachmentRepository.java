package com.theinngu32.common.repository;

import com.theinngu32.common.entity.StorageAttachment;
import com.theinngu32.common.entity.StorageBlob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StorageAttachmentRepository extends JpaRepository<StorageAttachment, Long> {

    @Query("""
            SELECT sa.recordType FROM StorageAttachment sa
            JOIN StorageBlob sb ON sa.storageBlob.id = sb.id
            WHERE sb.key = :key
          """)
    Optional<String> findRecordTypeByKey(@Param("key") String key);

    @Query("""
        SELECT sb.key FROM StorageBlob sb
        JOIN StorageAttachment sa ON sa.storageBlob.id = sb.id
        WHERE sa.recordType = :recordType
        AND sa.recordId = :recordId
        AND sa.name = :name
      """)
    Optional<String> findKeyByRecordTypeAndRecordIdAndName(String recordType, Long recordId, String name);

    void deleteByStorageBlob(StorageBlob blob);
}
