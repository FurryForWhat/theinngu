package com.theinngu32.common.repository;

import com.theinngu32.common.entity.TempYogi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.theinngu32.common.entity.Yogi;

import java.util.List;
import java.util.Optional;

@Repository
public interface YogiRepository extends JpaRepository<Yogi, Long>, JpaSpecificationExecutor<Yogi> {

    @Query("""
            SELECT y.blacklisted FROM Yogi y WHERE y.yogiId = :yogiId
       """)
    Optional<Boolean> isBlacklistedByYogiId(String yogiId);
    Optional<Yogi> findByYogiId(String yogiId);
    Optional<Yogi> findByNrc(String nrc);
}
