package com.theinngu32.common.repository;

import com.theinngu32.common.entity.YogiNrc;
import com.theinngu32.common.entity.constants.NrcType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface YogiNrcRepository extends JpaRepository<YogiNrc, Long>, JpaSpecificationExecutor<YogiNrc> {

    YogiNrc findYogiNrcByYogiId(Long yogiId);

    @Query ("""
    SELECT COUNT(*) > 0 FROM YogiNrc y
    WHERE y.postFixDigit = :postFixDigit
    AND y.type = :type
    AND y.nrcCode.id = :nrcCodeId
   """)
    boolean findYogiNrcByPostFixDigitAndTypeAndNrcCodeId(String postFixDigit, NrcType type, Long nrcCodeId);

    @Query("""
    SELECT COUNT(*) > 0 FROM YogiNrc y
    WHERE y.postFixDigit = :postFixDigit
    AND y.type = :type
    AND y.nrcCode.id = :nrcCodeId
    AND y.id <> :id
    """)
    boolean findYogiNrcByPostFixDigitAndTypeAndNrcCodeIdAndIdNot(String postFixDigit, NrcType type, Long nrcCodeId, Long id);


    @Query("""
        SELECT y.blacklisted
        FROM YogiNrc yn
                 JOIN Yogi y ON y.id = yn.yogi.id
                 JOIN NrcCode nc ON yn.nrcCode.id = nc.id
        WHERE nc.prefixCode = :prefixCode
        AND (nc.nameMm = :nameMM OR nc.nameEn = :nameEn)
        AND yn.type = :type
        AND yn.postFixDigit = :postFixDigit
    """)
    Optional<Boolean> isBlacklistedByNrc(String prefixCode, String nameMM, String nameEn, NrcType type, String postFixDigit);

}
