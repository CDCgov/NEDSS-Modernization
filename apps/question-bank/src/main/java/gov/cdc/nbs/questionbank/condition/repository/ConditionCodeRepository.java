package gov.cdc.nbs.questionbank.condition.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.cdc.nbs.questionbank.entity.condition.ConditionCode;

public interface ConditionCodeRepository extends JpaRepository<ConditionCode, String> {
    @Query("SELECT count(*) FROM ConditionCode WHERE ConditionCode.conditionShortNm=:name'")
    boolean checkConditionName(@Param("name") String name);
}
