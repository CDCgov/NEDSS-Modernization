package gov.cdc.nbs.questionbank.condition.repository;

import gov.cdc.nbs.questionbank.entity.condition.LdfPageSet;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface LdfPageSetRepository extends JpaRepository<LdfPageSet, String> {
  @Query("SELECT id FROM LdfPageSet")
  List<String> findAllIds();

  @Modifying
  @Transactional
  @Query(
      """
            UPDATE LdfPageSet l
            SET l.statusCd = l.conditionCd.statusCd
            WHERE l.conditionCd is not null
            """)
  void updateStatusBasedOnConditionCode();

  @Query("SELECT MAX(displayRow) + 1 FROM LdfPageSet")
  Short nextDisplayRow();

  @Query("SELECT MAX(nbsUid) + 1 FROM LdfPageSet")
  Integer nextNbsUid();
}
