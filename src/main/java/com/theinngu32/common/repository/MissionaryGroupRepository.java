package com.theinngu32.common.repository;

import com.theinngu32.common.entity.MissionaryGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MissionaryGroupRepository extends JpaRepository<MissionaryGroup, Long>, JpaSpecificationExecutor<MissionaryGroup> {
    MissionaryGroup findByName(String name);
}
