package gov.cdc.nbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.cdc.nbs.entity.NBSEntity;

public interface EntityRepository extends JpaRepository<NBSEntity, Long> {

}
