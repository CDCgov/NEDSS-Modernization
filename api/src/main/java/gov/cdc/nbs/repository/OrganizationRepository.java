package gov.cdc.nbs.repository;

import gov.cdc.nbs.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long>, QuerydslPredicateExecutor<Organization> {
        @Query("SELECT coalesce(max(o.id), 0) FROM Organization o")
        Long getMaxId();

}