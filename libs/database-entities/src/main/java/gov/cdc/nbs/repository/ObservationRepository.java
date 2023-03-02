package gov.cdc.nbs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import gov.cdc.nbs.entity.odse.Observation;

public interface ObservationRepository extends JpaRepository<Observation, Long>, QuerydslPredicateExecutor<Observation> {

	List<Observation> findByIdIn(List<Long> actuid);
}
