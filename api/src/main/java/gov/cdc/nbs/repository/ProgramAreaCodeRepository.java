package gov.cdc.nbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import gov.cdc.nbs.entity.srte.ProgramAreaCode;

public interface ProgramAreaCodeRepository
        extends JpaRepository<ProgramAreaCode, String>, QuerydslPredicateExecutor<ProgramAreaCode> {

}