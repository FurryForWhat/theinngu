package com.theinngu32.common.repository;

import com.theinngu32.common.entity.MeditationClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MeditationClassRepository extends JpaRepository<MeditationClass, Long>, JpaSpecificationExecutor<MeditationClass> {


}
