package gov.cdc.nbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import gov.cdc.nbs.entity.srte.ConditionCode;
import gov.cdc.nbs.entity.srte.CountryCode;

public interface ConditionCodeRepository
        extends JpaRepository<ConditionCode, String>, QuerydslPredicateExecutor<CountryCode> {

}
