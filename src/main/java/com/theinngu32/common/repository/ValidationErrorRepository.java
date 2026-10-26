package com.theinngu32.common.repository;

import com.theinngu32.common.entity.CommonError;
import com.theinngu32.common.entity.constants.errorMessage.ErrorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ValidationErrorRepository extends JpaRepository<CommonError,Long> {
    Optional<List<CommonError>> findByEntityNameAndRecordId(String entityName, Long recordId);
    Optional<CommonError> findByEntityNameAndFieldNameAndRecordIdAndErrorType(String className, String fieldName, Long recordId, ErrorType errorType);
}
