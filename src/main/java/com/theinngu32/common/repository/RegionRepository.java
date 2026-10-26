package com.theinngu32.common.repository;

import com.theinngu32.common.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RegionRepository extends JpaRepository<Region,Long>, JpaSpecificationExecutor<Region> {

}
