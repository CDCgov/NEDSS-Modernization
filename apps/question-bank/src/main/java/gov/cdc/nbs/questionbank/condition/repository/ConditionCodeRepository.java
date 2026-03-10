package gov.cdc.nbs.questionbank.condition.repository;

import gov.cdc.nbs.questionbank.entity.condition.ConditionCode;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ConditionCodeRepository
    extends JpaRepository<ConditionCode, String>, QuerydslPredicateExecutor<ConditionCode> {

  @Query("SELECT count(*) FROM ConditionCode c WHERE c.id=:id")
  long checkId(@Param("id") String id);

  @Query("SELECT count(*) FROM ConditionCode c WHERE c.conditionShortNm=:name")
  long checkConditionName(@Param("name") String name);

  @Modifying
  @Transactional
  @Query("UPDATE ConditionCode c SET c.statusCd=cast('A' as character) WHERE c.id =:id")
  int activateCondition(@Param("id") String id);

  @Modifying
  @Transactional
  @Query("UPDATE ConditionCode c SET c.statusCd=cast('I' as character) WHERE c.id =:id")
  int inactivateCondition(@Param("id") String id);

  @Query("SELECT MAX(nbsUid) + 2 FROM ConditionCode")
  long getNextNbsUid();

  List<ConditionCode> findByIdIn(List<String> ids);
}
