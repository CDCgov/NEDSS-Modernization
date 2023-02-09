package gov.cdc.nbs.patientlistener.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import gov.cdc.nbs.patientlistener.odse.TeleLocator;

public interface TeleLocatorRepository
extends JpaRepository<TeleLocator, Long>, QuerydslPredicateExecutor<TeleLocator> {
@Query("SELECT coalesce(max(t.id), 0) FROM TeleLocator t")
Long getMaxId();

}
