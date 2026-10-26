package com.theinngu32.common.repository;

import com.theinngu32.common.entity.StorageBlob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface StorageBlobRepository extends JpaRepository<StorageBlob, Long> {
    @Query("""
            SELECT sb.filename FROM StorageBlob sb
            WHERE sb.key = :key
          """)
    Optional<String> findFilenameByKey(@Param("key") String key);

    Optional<StorageBlob> findByKey(String key);
}
