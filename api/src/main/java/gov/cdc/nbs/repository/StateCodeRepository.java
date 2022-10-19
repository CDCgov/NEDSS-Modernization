package gov.cdc.nbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import gov.cdc.nbs.entity.srte.StateCode;

public interface StateCodeRepository extends JpaRepository<StateCode, String>, QuerydslPredicateExecutor<StateCode> {

}
