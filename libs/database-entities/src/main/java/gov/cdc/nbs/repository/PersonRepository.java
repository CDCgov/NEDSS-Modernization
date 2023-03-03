package gov.cdc.nbs.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import gov.cdc.nbs.entity.odse.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>, QuerydslPredicateExecutor<Person> {
	@Query("SELECT coalesce(max(p.id), 0) FROM Person p")
	Long getMaxId();

	Page<Person> findByIdIn(List<Long> ids, Pageable pageable);

	@Query(value="SELECT p.person_uid from Person p where p.person_parent_uid=:personParentUid", nativeQuery=true)
	List<Long> getPersonIdsByPersonParentId(@Param("personParentUid") Long personParentUid);
}