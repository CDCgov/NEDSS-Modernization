package gov.cdc.nbs.questionbank.condition.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.cdc.nbs.questionbank.entity.condition.ConditionCode;

public interface ConditionCodeRepository extends JpaRepository<ConditionCode, String> {

    @Query("SELECT count(*) FROM ConditionCode c WHERE c.id=:id")
    long checkId(@Param("id")String id);
    @Query("SELECT count(*) FROM ConditionCode c WHERE c.conditionShortNm=:name")
    long checkConditionName(@Param("name") String name);
}
