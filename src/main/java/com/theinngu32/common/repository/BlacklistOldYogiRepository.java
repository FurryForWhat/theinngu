package com.theinngu32.common.repository;

import com.theinngu32.common.entity.BlacklistOldYogi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlacklistOldYogiRepository extends JpaRepository<BlacklistOldYogi, Long>, JpaSpecificationExecutor<BlacklistOldYogi> {
    @Query("SELECT b FROM BlacklistOldYogi b WHERE b.oldYogi.id = :yogiId")
    Optional<List<BlacklistOldYogi>> findByYogiId(@Param("yogiId") Long yogiId);

    @Query("SELECT b FROM BlacklistOldYogi b WHERE b.createdBy.id = :createdById")
    Optional<List<BlacklistOldYogi>> findByCreatedById(@Param("createdById") Long createdById);

    @Query("SELECT b FROM BlacklistOldYogi b WHERE b.oldYogi.id = :yogiId AND b.createdBy.id = :createdById")
    Optional<BlacklistOldYogi> findByYogiIdAndCreatedById(@Param("yogiId") Long yogiId, @Param("createdById") Long createdById);

    boolean existsByOldYogiId(Long oldYogiId);
}
