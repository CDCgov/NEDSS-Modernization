package gov.cdc.nbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import gov.cdc.nbs.entity.odse.Organization;

@Repository
public interface OrganizationRepository
                extends JpaRepository<Organization, Long>, QuerydslPredicateExecutor<Organization> {
        @Query("SELECT coalesce(max(o.id), 0) FROM Organization o")
        Long getMaxId();

}