package gov.cdc.nbs.questionbank.condition.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.cdc.nbs.questionbank.entity.condition.LdfPageSet;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LdfPageSetRepository extends JpaRepository<LdfPageSet, String> {
    @Query("SELECT id FROM LdfPageSet")
    List<String> findAllIds();

    @Modifying
    @Transactional
    @Query("UPDATE LdfPageSet l " +
            "SET l.statusCd = (SELECT c.statusCd FROM ConditionCode c WHERE c.id = l.conditionCd) " +
            "WHERE EXISTS (SELECT 1 FROM ConditionCode c WHERE c.id = l.conditionCd)")
    void updateStatusBasedOnConditionCode();

    @Query("SELECT MAX(displayRow) + 1 FROM LdfPageSet")
    Short nextDisplayRow();

    @Query("SELECT MAX(nbsUid) + 1 FROM LdfPageSet")
    Integer nextNbsUid();
}
