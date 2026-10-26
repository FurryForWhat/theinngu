package com.theinngu32.common.repository;

import com.theinngu32.common.entity.TempYogi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TempYogiRepository extends JpaRepository<TempYogi, Long>, JpaSpecificationExecutor<TempYogi> {
    Optional<List<TempYogi>> findByCreatedById(Long createdById);

    @Query("""
        SELECT ti FROM TempYogi ti
        WHERE ti.recordType = :recordType
        AND ti.recordId = :recordId
    """)
    Optional<List<TempYogi>> findByRecordTypeAndRecordId(String recordType, Long recordId);

    Optional<List<TempYogi>> findByRecordType(String recordType);

    Optional<TempYogi> findByPassportAndRecordTypeAndRecordId(String passport, String recordType, Long recordId);

    Optional<TempYogi> findByYogiIdAndRecordTypeAndRecordId(String yogiId, String recordType, Long recordId);

    Optional<TempYogi> findByNrcAndRecordTypeAndRecordId(String nrc, String recordType, Long recordId);
}
