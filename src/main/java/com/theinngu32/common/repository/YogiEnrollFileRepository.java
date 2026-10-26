package com.theinngu32.common.repository;

import com.theinngu32.common.entity.importfile.YogiEnrollFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YogiEnrollFileRepository extends JpaRepository<YogiEnrollFile, Long> {
}
