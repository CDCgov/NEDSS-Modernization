package gov.cdc.nbs.questionbank.condition.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;

import gov.cdc.nbs.questionbank.entity.condition.ConditionCode;

public interface ConditionCodeRepository extends JpaRepository<ConditionCode, String> {

    @Query("SELECT count(*) FROM ConditionCode c WHERE c.id=:id")
    long checkId(@Param("id")String id);
    @Query("SELECT count(*) FROM ConditionCode c WHERE c.conditionShortNm=:name")
    long checkConditionName(@Param("name") String name);

    @Query("SELECT c FROM ConditionCode c WHERE c.id LIKE %:id% OR c.conditionShortNm LIKE %:conditionShortNm% OR c.progAreaCd LIKE %:progAreaCd% OR c.familyCd LIKE %:familyCd% OR c.coinfectionGrpCd LIKE %:coinfectionGrpCd% OR c.nndInd =:nndInd OR c.investigationFormCd LIKE %:investigationFormCd% OR c.statusCd =:statusCd")
    Page<ConditionCode> findByField(@Param("id")String id, @Param("conditionShortNm") String conditionShortNm, @Param("progAreaCd") String progAreaCd,
                                    @Param("familyCd")String familyCd, @Param("coinfectionGrpCd")String coinfectionGrpCd, @Param("nndInd")Character nndInd, @Param("investigationFormCd") String investigationFormCd,
                                    @Param("statusCd")Character statusCd, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE ConditionCode c SET c.statusCd='A' WHERE c.id =:id")
    int activateCondition(@Param("id") String id);

    @Modifying
    @Transactional
    @Query("UPDATE ConditionCode c SET c.statusCd='I' WHERE c.id =:id")
    int inactivateCondition(@Param("id") String id);
}
