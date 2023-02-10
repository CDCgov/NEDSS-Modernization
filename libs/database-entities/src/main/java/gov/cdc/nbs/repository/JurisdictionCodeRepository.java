package gov.cdc.nbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import gov.cdc.nbs.entity.srte.JurisdictionCode;

public interface JurisdictionCodeRepository
        extends JpaRepository<JurisdictionCode, String>, QuerydslPredicateExecutor<JurisdictionCode> {

}
