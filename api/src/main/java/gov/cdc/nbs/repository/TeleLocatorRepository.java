package gov.cdc.nbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import gov.cdc.nbs.entity.odse.TeleLocator;

public interface TeleLocatorRepository
        extends JpaRepository<TeleLocator, Long>, QuerydslPredicateExecutor<TeleLocator> {

}
