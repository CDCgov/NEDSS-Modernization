package gov.cdc.nbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import gov.cdc.nbs.entity.odse.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>, QuerydslPredicateExecutor<Person> {

}
