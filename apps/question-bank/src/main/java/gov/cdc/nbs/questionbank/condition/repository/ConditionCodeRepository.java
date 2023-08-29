package gov.cdc.nbs.questionbank.condition.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import gov.cdc.nbs.questionbank.entity.condition.ConditionCode;

public interface ConditionCodeRepository
        extends JpaRepository<ConditionCode, String>, QuerydslPredicateExecutor<ConditionCode> {

    @Query("SELECT count(*) FROM ConditionCode c WHERE c.id=:id")
    long checkId(@Param("id") String id);

    @Query("SELECT count(*) FROM ConditionCode c WHERE c.conditionShortNm=:name")
    long checkConditionName(@Param("name") String name);

    @Query("SELECT MAX(nbsUid) + 2 FROM ConditionCode")
    long getNextNbsUid();
    
    List<ConditionCode> findByIdIn(List<String> ids);

}
