package gov.cdc.nbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import gov.cdc.nbs.entity.odse.PostalLocator;

public interface PostalLocatorRepository
        extends JpaRepository<PostalLocator, Long>, QuerydslPredicateExecutor<PostalLocator> {

}
