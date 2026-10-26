package com.theinngu32.common.repository;

import com.theinngu32.common.entity.NrcCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface NrcCodeRepository extends JpaRepository<NrcCode, Long>,
        JpaSpecificationExecutor<NrcCode> {

    NrcCode findByNameMmAndNameEnAndPrefixCode(String nameMM, String nameEN, int prefixCode);

}