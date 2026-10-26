package com.theinngu32.common.repository;

import com.theinngu32.common.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CityRepository extends JpaRepository<City,Long>, JpaSpecificationExecutor<City> {
}
