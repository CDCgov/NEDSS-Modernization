package gov.cdc.nbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import gov.cdc.nbs.entity.odse.PublicHealthCase;

public interface PublicHealthCaseRepository
        extends JpaRepository<PublicHealthCase, Long>, QuerydslPredicateExecutor<PublicHealthCase> {

}
