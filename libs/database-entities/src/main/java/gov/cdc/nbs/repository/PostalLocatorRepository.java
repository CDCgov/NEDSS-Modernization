package gov.cdc.nbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import gov.cdc.nbs.entity.odse.PostalLocator;

public interface PostalLocatorRepository
        extends JpaRepository<PostalLocator, Long>, QuerydslPredicateExecutor<PostalLocator> {

    @Query("SELECT coalesce(max(p.id), 0) FROM PostalLocator p")
    Long getMaxId();
}
