package gov.cdc.nbs.repository;

import gov.cdc.nbs.entity.srte.ConditionCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ConditionCodeRepository
                extends JpaRepository<ConditionCode, String>, QuerydslPredicateExecutor<ConditionCode> {
}
