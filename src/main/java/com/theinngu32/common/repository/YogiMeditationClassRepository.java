package com.theinngu32.common.repository;

import com.theinngu32.common.entity.YogiMeditationClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface YogiMeditationClassRepository extends JpaRepository<YogiMeditationClass, Long> {
    @Query("""
        SELECT ymc.joinedDate FROM YogiMeditationClass ymc
        WHERE ymc.yogi.id = :yogiId AND ymc.meditationClass.id = :classId
    """)
    Optional<LocalDate> findJoinedDateByYogiIdAndClassId(Long yogiId, Long classId);

    @Query("""
        SELECT ymc.blacklisted FROM YogiMeditationClass ymc
        WHERE ymc.yogi.id = :yogiId AND ymc.meditationClass.id = :classId
    """)
    Optional<Boolean> isBlacklistYogiByClassId(Long yogiId, Long classId);

}
