package com.theinngu32.common.repository;

import com.theinngu32.common.entity.OldYogi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OldYogiRepository extends JpaRepository<OldYogi,Long> {
    Optional<List<OldYogi>> findByNrc(String nrc);

    Optional<OldYogi> findOldYogiByByNrc(String nrc);
    @Query("""
            SELECT o.blacklisted FROM OldYogi o WHERE o.nrc = :nrc
       """)
    Optional<Boolean> isBlacklistedByNrc(String nrc);
}
