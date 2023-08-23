package gov.cdc.nbs.questionbank.condition.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.cdc.nbs.questionbank.entity.condition.LdfPageSet;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LdfPageSetRepository extends JpaRepository<LdfPageSet, String> {
    @Query("SELECT id FROM LdfPageSet")
    List<String> findAllIds();

    @Query("SELECT MAX(displayRow) + 1 FROM LdfPageSet")
    Short nextDisplayRow();

    @Query("SELECT MAX(nbsUid) + 1 FROM LdfPageSet")
    Integer nextNbsUid();
}
