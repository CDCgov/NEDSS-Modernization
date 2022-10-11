package gov.cdc.nbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import gov.cdc.nbs.entity.odse.Act;

public interface ActRepository extends JpaRepository<Act, Long>, QuerydslPredicateExecutor<Act> {

}
