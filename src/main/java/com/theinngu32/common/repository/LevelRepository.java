package com.theinngu32.common.repository;

import com.theinngu32.common.entity.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LevelRepository extends JpaRepository<Level,Long>, JpaSpecificationExecutor<Level> {
}
