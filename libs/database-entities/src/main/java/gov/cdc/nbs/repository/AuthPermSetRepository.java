package gov.cdc.nbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.cdc.nbs.entity.odse.AuthPermSet;

public interface AuthPermSetRepository extends JpaRepository<AuthPermSet, Long> {

}
