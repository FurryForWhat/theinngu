package com.theinngu32.common.repository;

import com.theinngu32.common.entity.SubMissionaryGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubMissionaryGroupRepository extends JpaRepository<SubMissionaryGroup, Long>{

    Optional<List<SubMissionaryGroup>> findByMissionaryGroupId(Long id);
}
