package com.theinngu32.common.repository;

import com.theinngu32.common.entity.BlacklistYogi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlacklistYogiRepository extends JpaRepository<BlacklistYogi, Long>, JpaSpecificationExecutor<BlacklistYogi> {
    @Query("SELECT b FROM BlacklistYogi b WHERE b.yogi.id = :yogiId")
    Optional<List<BlacklistYogi>> findByYogiId(@Param("yogiId") Long yogiId);

    @Query("SELECT b FROM BlacklistYogi b WHERE b.createdBy.id = :createdById")
    Optional<List<BlacklistYogi>> findByCreatedById(@Param("createdById") Long createdById);

    @Query("SELECT b FROM BlacklistYogi b WHERE b.yogi.id = :yogiId AND b.createdBy.id = :createdById")
    Optional<BlacklistYogi> findByYogiIdAndCreatedById(@Param("yogiId") Long yogiId, @Param("createdById") Long createdById);

    boolean existsByYogiId(Long yogiId);
}
