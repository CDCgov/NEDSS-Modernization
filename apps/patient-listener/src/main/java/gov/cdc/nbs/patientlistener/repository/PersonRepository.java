package gov.cdc.nbs.patientlistener.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import gov.cdc.nbs.patientlistener.odse.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>, QuerydslPredicateExecutor<Person> {
	@Query("SELECT coalesce(max(p.id), 0) FROM Person p")
	Long getMaxId();

	Page<Person> findByIdIn(List<Long> ids, Pageable pageable);

	Optional<Person> findById(Long id);

}